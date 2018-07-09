import com.sun.javafx.geom.Vec2d;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Represents a particle in the explosion.
 */
class Point {

    // Physical
    private Vec pos;
    private Vec speed;

    // Logical
    private Point parent;
	private double vision;
    
    // Visual
    private Color color;
    private int size;

    Point () {
        generateRandomPoint();
    }

    private void generateRandomPoint () {
        Random r = new Random();

        pos = new Vec(r.nextDouble() * Properties.WIDTH, r.nextDouble() * Properties.HEIGHT);
        //speed = new Vec(r.nextDouble() - 0.5, r.nextDouble() - 0.5);
        lookAt(new Vec(Properties.WIDTH/2, Properties.HEIGHT/2));

        vision = 10;
        color = new Color(236, 240, 241);
        size = 2;
    }

    void move() {
        if(pos.x < 5 && speed.x < 0) speed.x = 0;
	    if(pos.x > Properties.WIDTH - 5 && speed.x > 0) speed.x = 0;
	    if(pos.y < 5 && speed.y < 0) speed.y = 0;
	    if(pos.y > Properties.HEIGHT - 5 && speed.y > 0) speed.y = 0;
	
	    pos.add(speed);
    }

    void increment () {
        pos.add(speed);
    }

    void action(Vec v) {
        lookAt(v);
        pos.add(speed);
    }

    void gobetween(List<Point> points) {
        int size = points.size();
        Random r = new Random();
        int a = r.nextInt(size);
        int b = r.nextInt(size);
        lookAt(points.get(a).pos.plus(points.get(b).pos).times(0.5));
        move();
    }

    void goToRandomPoint(List<Point> points) {
        int index = points.indexOf(this);
        int next = (index+1)%points.size();
        lookAt(points.get(next).pos);
        move();
    }

    void act1(List<Point> points) {
        size = points.size()+2;
        if(points.size() < 1) {
            lookAt(new Vec(Properties.WIDTH/2.0, Properties.HEIGHT/2.0));
            move();
        } else {
            Vec sum = new Vec(0, 0);
            for (int i = 0; i < points.size(); i++) {
                double dst = pos.distance(points.get(i).pos);
                if(dst < 5) {
                    lookAt(points.get(i).pos);
                    speed.mul(-1.0);
                    move();
                    return;
                }
                sum.add(points.get(i).pos);
            }
            sum.mul(1.0 / points.size());
            lookAt(sum);
            if (points.size() > 2) {
                speed.mul(-1.0);
            }
            move();
        }
    }


    int getX () {
        return (int)Math.round(pos.x);
    }

    int getY () {
        return (int)Math.round(pos.y);
    }

    Vec getPos() {
        return pos;
    }

    public void lookAt(Vec v) {
        speed = v.minus(pos);
        speed.normalize();
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("Position:\t\t" + pos.toString() + "\n");
        sb.append("Speed:\t\t" + speed.toString() + "\n");
        return new String(sb);
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }
	
	public double getVision() {
		return vision;
	}
}