import java.awt.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

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
        listOfPoints.get(0).setColor(new Color(255-192, 255-57, 255-43));
        listOfPoints.get(0).setSize(10);
    }

    /**
     * Move each point to its next location in the blast.
     */
    void incrementPoints () {
        /*for (int i = 0; i < listOfPoints.size(); i++) {
            listOfPoints.get(i).goToRandomPoint(listOfPoints);
        }*/
        for (int i = 0; i < listOfPoints.size(); i++) {
            listOfPoints.get(i).act1(getPointsNear(listOfPoints.get(i), 10));
        }
    }

    public ArrayList<Point> getPointsNear(Point p, double d) {
        ArrayList<Point> nearPoints = new ArrayList<>();o
        for (int i = 0; i < listOfPoints.size(); i++) {
            Point po = listOfPoints.get(i);
            if(po.getPos().distance(p.getPos()) < d && po != p) nearPoints.add(po);
        }
        return nearPoints;
    }

    void paint (Graphics g) {

        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.clipRect(0, 0, Properties.WIDTH, Properties.HEIGHT);

        graphics.setColor(Properties.COLOR);

        drawStatic(graphics);

        for (Point p : listOfPoints) {
            graphics.setColor(p.getColor());
            graphics.fillOval(p.getX() - p.getSize() / 2, p.getY() - p.getSize() / 2, p.getSize(), p.getSize());
        }
    }

    void drawStatic(Graphics2D graphics) {
        int centerSize = 3;
        graphics.setColor(Color.CYAN);
        graphics.fillOval(Properties.WIDTH/2 - centerSize / 2, Properties.HEIGHT/2 - centerSize / 2, centerSize, centerSize);
    }

}