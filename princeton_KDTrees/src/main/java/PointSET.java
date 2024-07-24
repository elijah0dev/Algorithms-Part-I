import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> treeSet;

    // construct an empty set of points
    public PointSET() {
        treeSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return treeSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        treeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return treeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : treeSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {

                    private Stack<Point2D> stack = new Stack<>();

                    {
                        for (Point2D point : treeSet) {
                            if (rect.contains(point)) stack.push(point);
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return !stack.isEmpty();
                    }

                    @Override
                    public Point2D next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return stack.pop();
                    }
                };
            }
        };
    }



    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (treeSet.isEmpty()) return null;

        Point2D closet = treeSet.first();

        for (Point2D point: treeSet){
            if (p.distanceSquaredTo(point) < p.distanceSquaredTo(closet)) closet = point;
        }
        return closet;
    }

/*
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (treeSet.isEmpty()) return null;

        Point2D ceiling = treeSet.ceiling(p);
        Point2D floor = treeSet.floor(p);

        if (ceiling == null) return floor;
        if (floor == null) return ceiling;

        double ceilingDistance = p.distanceSquaredTo(ceiling);
        double floorDistance = p.distanceSquaredTo(floor);

        if (ceilingDistance < floorDistance) return ceiling;
        return floor;
    }

 */

}