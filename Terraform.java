import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

class Terraform {
    static final int[] DAYLIMITS = {
        37 * 3,
        310 * 3,
        3367 * 3,
        14770 * 3
    };

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            File problemFile = new File((i + 1) + ".txt");
            File solutionFile = new File((i + 1) + "_terraform.txt");
            if (solutionFile.exists()) {
                solutionFile.delete();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(solutionFile))) {
                World world = World.fromConfig(problemFile);
                PathFinder pathFinder = new BackPropogationPathFinder();
                Path path = pathFinder.findPath(world, Terraform.DAYLIMITS[i]);
                System.out.println("Path found:");
                PathFinder.displayPath(path);
                PathFinder.scorePath(path);
                writer.write(path.formatPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}