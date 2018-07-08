import com.sun.javafx.geom.Vec2d;

import java.util.Random;

/**
 * Represents a particle in the explosion.
 */
class Point {

    private Vec pos;
    private Vec speed;

    Point () {
        generateRandomPoint();
    }

    private void generateRandomPoint () {
        Random r = new Random();

        pos = new Vec(r.nextDouble() * Properties.WIDTH, r.nextDouble() * Properties.HEIGHT);
        speed = new Vec(r.nextDouble() - 0.5, r.nextDouble() - 0.5);
    }

    void increment () {
        pos.add(speed);
    }

    void action(Vec v) {
        lookAt(v);
        pos.add(speed);
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
}