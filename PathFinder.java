public abstract class PathFinder {
    public abstract Path findPath();

    public void displayPath(Path path) {
        for (World.Node node : path.getPath()) {
            System.out.println("Node: " + node.x + ", " + node.y);
        }
        // make sum for path points
    }

}
