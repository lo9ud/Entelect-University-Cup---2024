import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BackPropogationPathFinder extends PathFinder {

    @Override
    public Path findPath(World world) {
        int radius = world.getRadius();

        // Find best straight-line path
        float max_score = 0;
        int best_x = 0;
        for (int x_val = -radius; x_val <= radius; x_val++) {
            float score = 0;

            for (int y_val = radius; y_val >= -radius; y_val--) {
                World.Node node = world.getNode(x_val, y_val);
                score += PathFinder.nodeScore(node, x_val);
            }
            if (score > max_score) {
                max_score = score;
                best_x = x_val;
            }
        }

        final int new_best_r = best_x;

        ArrayList<World.Node> path_nodes = new ArrayList<>();
        path_nodes.add(world.northPole);
        path_nodes.addAll(world.getNodes().stream().filter((node) -> node.x == new_best_r)
                .sorted(new Comparator<World.Node>() {
                    @Override
                    public int compare(World.Node o1, World.Node o2) {
                        return o2.y - o1.y;
                    }
                }).collect(Collectors.toList()));
        path_nodes.add(world.southPole);

        Path path = new Path();
        for (World.Node node : path_nodes) {
            path.addNode(node);
        }
        // Look for best transform for path

        // Repeat ad infinitum until path length exceeds limit

        return path;
    }

    public static void main(String[] args) {
        try {
            World world = World.fromConfig(new File("1.txt"));
            PathFinder pathFinder = new BackPropogationPathFinder();
            Path path = pathFinder.findPath(world);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
