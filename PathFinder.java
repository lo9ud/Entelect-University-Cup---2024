public abstract class PathFinder {
    public abstract Path findPath(World world);

    static final int[] BIOME_VALUE_PER_DAY = {
        1,
        14,
        28,
        42,
        57,
        71,
        85,
        100,
    };

    public static float nodeScore(World.Node node, int index) {
        int days = index / 3 + 1;
        return node.multiplier * PathFinder.BIOME_VALUE_PER_DAY[node.biome] * days;
    }

    public void displayPath(Path path) {
        for (World.Node node : path.getPath()) {
            System.out.println("Node: " + node.x + ", " + node.y);
        }
        // make sum for path points
    }

}
