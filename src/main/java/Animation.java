import java.awt.*;
import java.awt.geom.Line2D;
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
        for (int i = 0; i < listOfPoints.size(); i++) {
            listOfPoints.get(i).act1(getPointsNear(listOfPoints.get(i), 10));
        }
    }

    public ArrayList<Point> getPointsNear(Point p, double d) {
        ArrayList<Point> nearPoints = new ArrayList<>();
        for (int i = 0; i < listOfPoints.size(); i++) {
            Point po = listOfPoints.get(i);
            if(po.getPos().distance(p.getPos()) < p.getVision() && po != p) nearPoints.add(po);
        }
        return nearPoints;
    }

    void paint (Graphics g) {

        Graphics2D graphics = (Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.clipRect(0, 0, Properties.WIDTH, Properties.HEIGHT);
	    
        drawStatic(graphics);

        for (Point p : listOfPoints) {
            drawPoint(graphics, p.getPos(), p.getSize(), p.getColor());
        }
    }

    void drawStatic(Graphics2D graphics) {
        drawPoint(graphics, new Vec(Properties.WIDTH/2, Properties.HEIGHT/2), 3, Color.green);
        graphics.setColor(Color.cyan);
        graphics.setStroke(new BasicStroke(1));
        
        // BORDERS
        drawLine(graphics, 0, 0, Properties.WIDTH, 0, 1, Color.cyan);
	    drawLine(graphics, Properties.WIDTH, 0, Properties.WIDTH, Properties.HEIGHT, 1, Color.cyan);
	    drawLine(graphics, Properties.WIDTH, Properties.HEIGHT, 0, Properties.HEIGHT, 1, Color.cyan);
	    drawLine(graphics, 0, Properties.HEIGHT, 0, 0, 1, Color.cyan);
    }
    
    void drawPoint(Graphics2D graphics, Vec v, int size, Color color) {
	    graphics.setColor(color);
	    int sizeZ = (int)(size * Window.zoom);
	    int x = (int)((v.x - Properties.WIDTH + Window.panX)*Window.zoom) + Window.panX - sizeZ / 2;
	    int y = (int)((v.y - Properties.HEIGHT + Window.panY)*Window.zoom) + Window.panY - sizeZ / 2;
	    graphics.fillOval(x, y, sizeZ, sizeZ);
    }

    void drawLine(Graphics2D graphics, int x1, int y1, int x2, int y2, int width, Color color) {
	    int X1 = (int)((x1 - Properties.WIDTH + Window.panX)*Window.zoom) + Window.panX;
	    int Y1 = (int)((y1 - Properties.HEIGHT + Window.panY)*Window.zoom) + Window.panY;
	    int X2 = (int)((x2 - Properties.WIDTH + Window.panX)*Window.zoom) + Window.panX;
	    int Y2 = (int)((y2 - Properties.HEIGHT + Window.panY)*Window.zoom) + Window.panY;
	    graphics.setColor(color);
	    graphics.setStroke(new BasicStroke((int)(width*Window.zoom)));
    	graphics.draw(new Line2D.Float(X1, Y1, X2, Y2));
    }
    
}