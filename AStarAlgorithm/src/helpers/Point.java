package helpers;

public class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Point p) {
        if (this.x == p.x && this.y == p.y) {
            return 0;
        }

        if (this.x > p.x && this.y > p.y) {
            return 1;
        }

        return -1;
    }

    @Override
     public boolean equals(Object obj) {
        boolean eq = false;
        if(obj instanceof Point){
            Point p = (Point) obj;
            eq = (p.getX() == this.getX()) && (p.getY() == this.getY());
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return (x + y);
    }
}