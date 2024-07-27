import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

class Terraform {
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
                Path path = pathFinder.findPath(world);
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