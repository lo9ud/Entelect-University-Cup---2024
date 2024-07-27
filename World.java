import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class World {
    public static void main(String[] args) {
        System.out.println("Starting world instantiation...");
        try {
            World world = World.fromConfig(new File("1.txt"));
            System.out.println("World instantiated.");
            Display display = new Display();
            display.display(world);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    static class Node {
        public int x;
        public int y;
        public int biome;
        public float multiplier;

        private Node(int x, int y, int biome, float multiplier) {
            this.x = x;
            this.y = y;
            this.biome = biome;
            this.multiplier = multiplier;
        }

    }

    private int radius;
    private ArrayList<Node> nodes;

    private World(ArrayList<Node> nodes, int r) {
        this.radius = r;
        this.nodes = nodes;
    }

    public int getRadius() {
        return this.radius;
    }

    public ArrayList<Node> getNodes() {
        return this.nodes;
    }

    public static World fromConfig(File inFile) throws FileNotFoundException {
        return World.fromString(new BufferedReader(new FileReader(inFile)));
    }

    public static World fromString(BufferedReader in) {
        Stream <String> lines = in.lines();
        ArrayList<Node> nodes = new ArrayList<>();

        for (String line : lines.collect(Collectors.toList())) {
            String[] parts = line.substring(1, line.length() - 1).split(Pattern.quote(";"));
            int[] coords = Arrays.stream(parts[0].substring(1, parts[0].length() - 1).split(Pattern.quote(",")))
                    .mapToInt(Integer::parseInt).toArray();
            int biome;
            float multiplier;
            if (parts.length > 1) {
                biome = Integer.parseInt(parts[1]);
                multiplier = Float.parseFloat(parts[2]);
            } else {
                biome = 0;
                multiplier = 0;
            }
            nodes.add(new Node(coords[0], coords[1], biome, multiplier ));
        }
        return new World(nodes, nodes.get(0).y*2 + 1);
    }
}