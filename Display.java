public class Display {
    public Display() {
    }

    private float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public void display(World world) {
        int height = 800;
        int width = 1800;
        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(width/2, height/2, width/2, height/2);
        StdDraw.setFont();
        StdDraw.show(0);

        for (World.Node node : world.getNodes()) {
            float half_r = (float) (world.getRadius()) / 2;
            float draw_x = this.map(node.x, -half_r, half_r, 20, width -20);
            float draw_y = this.map(node.y, -half_r - 1, half_r + 1, 20, height-20);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(draw_x, draw_y, 25);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(draw_x, draw_y+9, "B: "+ node.biome);
            StdDraw.text(draw_x, draw_y-9, "M: " + node.multiplier);
        }
        StdDraw.show();
    }
}