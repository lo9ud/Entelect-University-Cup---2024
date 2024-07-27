import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BackPropogationPathFinder extends PathFinder {

    @Override
    public Path findPath(World world, int limit) {
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

        Path best_path = null;
        float best_score = 0;

        int remaining_cache = 0;
        int remaining_retries = 0;
        int remaining = limit - path_nodes.size() + 1;
        // Look for best transform for path
        while (remaining > 0) {
            if (remaining == remaining_cache) {
                remaining_retries++;
            } else {
                remaining_cache = remaining;
                remaining_retries = 0;
            }
            if (remaining_retries > 3) {
                break;
            }
            System.out.println("Remaining: " + remaining);
            // locate optimal cut position y value
            for (int y_comp = world.getRadius(); y_comp > -world.getRadius(); y_comp--) {
                // System.out.println("Cutting at y_comp: " + y_comp);
                int x_limit = Math.min(remaining, world.getRadius());
                // Find best path:
                // - Cut path at y_comp
                // - Extend path to x_comp
                // - extend path to south pole

                // check all possible x values around the world within limit
                for (int x_comp = -x_limit; x_comp < x_limit; x_comp++) {
                    // System.out.println("Extending to x_comp: " + x_comp);
                    // Cut path at y_comp (take nodes until we hit the y_comp level)
                    final int final_y_comp = y_comp;
                    ArrayList<World.Node> new_path = path_nodes.stream().takeWhile((node) -> node.y != final_y_comp)
                            .collect(Collectors.toCollection(ArrayList::new));

                    World.Node tip = new_path.get(new_path.size() - 1);

                    // Extend path from tip to x_comp
                    // System.out.println("Extending to x_comp: " + x_comp);
                    while (tip.x != x_comp) {
                        // System.out.println("Tip: " + tip.x + ", " + tip.y + " extending to x_comp: " + x_comp
                        //         + " world radius: " + world.getRadius());
                        World.Node new_node = world
                                .getNode((tip.x - (int) Math.signum(tip.x - x_comp)) % (world.getRadius() + 1), tip.y);
                        new_path.add(new_node);
                        tip = new_node;
                    }

                    // Extend path to south pole
                    // System.out.println("Extending to south pole");
                    while (tip.y > -world.getRadius()) {
                        World.Node new_node = world.getNode(tip.x, tip.y - 1);
                        System.out.println("adding node: " + new_node.x + ", " + new_node.y);
                        new_path.add(new_node);
                        tip = new_node;
                    }
                    Path tmp_path = new Path(new_path);
                    float score = PathFinder.pathScore(tmp_path);
                    // System.out.println("Score: " + score);

                    if (score > best_score) {
                        System.out.println("Updating best path - score: " + score);
                        best_score = score;
                        best_path = tmp_path;
                    }
                }

            }

            remaining = remaining - Math.abs(path_nodes.size() - best_path.getPath().size());
            path_nodes = best_path.getPath();
        }

        // Repeat ad infinitum until path length exceeds limit
        path_nodes.add(world.southPole);
        return new Path(path_nodes);
    }
}
