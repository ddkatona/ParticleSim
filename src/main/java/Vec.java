public class Vec {
    /**
     * The x coordinate.
     */
    public double x;

    /**
     * The y coordinate.
     */
    public double y;

    public Vec() { }

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec(Vec v) {
        set(v);
    }

    public void set(Vec v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
	
    /*
     * Returns the distance from this <code>Vec2d</code> to a
     * specified <code>Vec2d</code>.
     *
     * @param v the specified point to be measured
     *           against this <code>Vec2d</code>
     * @return the distance between this <code>Vec2d</code> and
     * the specified <code>Vec2d</code>.
     */
    public double distance(Vec v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        //if(vx > Properties.WIDTH * 0.5) vx = Properties.WIDTH - vx;
	    //if(vy > Properties.HEIGHT * 0.5) vy = Properties.HEIGHT - vy;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public void add(Vec v) {
        x += v.x;
        y += v.y;
    }

    public void sub(Vec v) {
        x -= v.x;
        y -= v.y;
    }

    public Vec plus(Vec v) {
        return new Vec(this.x + v.x, this.y + v.y);
    }

    public Vec minus(Vec v) {
	    double vx = this.x - v.x;
	    double vy = this.y - v.y;
    	return new Vec(vx, vy);
    }

    public void mul(double d) {
        x *= d;
        y *= d;
    }

    public Vec times(double n) {
        return new Vec(this.x * n, this.y * n);
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    }

    public void normalize() {
        this.mul(1.0/length());
    }

    public void mod() {
        x = x%Properties.WIDTH;
        y = y%Properties.HEIGHT;
    }

    /**
     * Returns the hashcode for this <code>Vec2d</code>.
     * @return      a hash code for this <code>Vec2d</code>.
     */
    @Override
    public int hashCode() {
        long bits = 7L;
        bits = 31L * bits + Double.doubleToLongBits(x);
        bits = 31L * bits + Double.doubleToLongBits(y);
        return (int) (bits ^ (bits >> 32));
    }

    /**
     * Determines whether or not two 2D points or vectors are equal.
     * Two instances of <code>Vec2d</code> are equal if the values of their
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     * @param obj an object to be compared with this <code>Vec2d</code>
     * @return <code>true</code> if the object to be compared is
     *         an instance of <code>Vec2d</code> and has
     *         the same values; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vec) {
            Vec v = (Vec) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    /**
     * Returns a <code>String</code> that represents the value
     * of this <code>Vec2d</code>.
     * @return a string representation of this <code>Vec2d</code>.
     */
    @Override
    public String toString() {
        return "Vec2d[" + x + ", " + y + "]";
    }
}
