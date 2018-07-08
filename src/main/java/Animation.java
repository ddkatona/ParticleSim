import java.awt.*;
import java.util.ArrayList;

/**
 * Contains and executes the animation.
 */
class Animation {

    private ArrayList<Point> listOfPoints;

    Animation() {
        listOfPoints = new ArrayList<>();
        generatePoints();
    }

    /**
     * Generate points with random velocities.
     */
    private void generatePoints () {
        for (int i = 0; i < Properties.POINTS; i++) {
            Point p = new Point();
            listOfPoints.add(p);
        }
    }

    /**
     * Move each point to its next location in the blast.
     */
    void incrementPoints () {
        Point last = listOfPoints.get(listOfPoints.size()-1);
        listOfPoints.get(0).action(last.getPos());
        for (int i = 1; i < listOfPoints.size(); i++) {
            //listOfPoints.get(i).increment();
            Point prev = listOfPoints.get(i-1);
            listOfPoints.get(i).action(prev.getPos());
        }
    }

    void paint (Graphics g) {

        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.clipRect(0, 0, Properties.WIDTH, Properties.HEIGHT);

        graphics.setColor(Properties.COLOR);

        for (Point p : listOfPoints) {
            graphics.fillOval(p.getX() - Properties.SIZE / 2, p.getY() - Properties.SIZE / 2, Properties.SIZE, Properties.SIZE);
        }
    }

}