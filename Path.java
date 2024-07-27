import java.util.ArrayList;;

public class Path {
    private ArrayList<World.Node> path;
    public Path () {
        this.path = new ArrayList<>();
    }

    public void addNode(World.Node node) {
        this.path.add(node);
    }

    public ArrayList<World.Node> getPath() {
        return this.path;
    }

    public void extendPath(World.Node start, World.Node end, World.Node[] extension) {
        int start_i = this.path.indexOf(start);
        int end_i = this.path.indexOf(end);
        if (start_i == -1 || end_i == -1) {
            throw new IllegalArgumentException("Start or end node not in path.");
        }
        if (Math.abs(start_i - end_i) != 1) {
            throw new IllegalArgumentException("Start and end nodes are not adjacent.");
        }
        ArrayList<World.Node> left = new ArrayList<>(this.path.subList(0, start_i + 1));
        ArrayList<World.Node> right = new ArrayList<>(this.path.subList(end_i, this.path.size()));
        this.path = new ArrayList<>(left);
        for (World.Node node : extension) {
            this.path.add(node);
        }
        this.path.addAll(right);
    }

    public void alternatePath(World.Node[] pivots, World.Node[] corners) {
        int start_i = this.path.indexOf(pivots[0]);
        int end_i = this.path.indexOf(pivots[1]);
        if (start_i == -1 || end_i == -1) {
            throw new IllegalArgumentException("Start or end node not in path.");
        }
        if (Math.abs(start_i - end_i) != 2) {
            throw new IllegalArgumentException("Start and end nodes are not adjacent.");
        }
        if (this.path.indexOf(corners[0]) != -1 && this.path.indexOf(corners[1]) != -1) {
            throw new IllegalArgumentException("Corners already in path.");
        } else if (this.path.indexOf(corners[0]) != -1) {
            this.path.set(this.path.indexOf(corners[0]), corners[1]);
        } else if (this.path.indexOf(corners[1]) != -1) {
            this.path.set(this.path.indexOf(corners[1]), corners[0]);
        } else {
            throw new IllegalArgumentException("Corners not in path.");
        }
    }
}
