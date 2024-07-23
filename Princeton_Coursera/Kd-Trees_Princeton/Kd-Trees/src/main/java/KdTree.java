import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        root = insert(root, p, 1, new RectHV(0, 0, 1, 1)); //maybe make rectHV a private variable?
    }

    private Node insert(Node node, Point2D point, int orientation, RectHV rectHV) {
        if (node == null) {
            size++;
            return new Node(point, rectHV); // i think size++ works here
        }
        if (orientation == 1) orientation--;
        else orientation++;

        if (orientation == 0) {
            if (Double.compare(point.x(), node.point.x()) == -1) {
                node.left = insert(node.left, point, orientation, new RectHV(rectHV.xmin(), rectHV.ymin(), node.point.x(), rectHV.ymax()));
            } else if (!(point.compareTo(node.point) == 0)) {
                node.right = insert(node.right, point, orientation, new RectHV(node.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
            }
        } else {
            if (Double.compare(point.y(), node.point.y()) == -1) {
                node.left = insert(node.left, point, orientation, new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), node.point.y()));
            } else if (!(point.compareTo(node.point) == 0)) {
                node.right = insert(node.right, point, orientation, new RectHV(rectHV.xmin(), node.point.y(), rectHV.xmax(), rectHV.ymax()));
            }
        }

        return node;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node node = root;
        int orientation = 1;

        while (node != null) {
            if (orientation == 1) orientation--;
            else orientation++;

            if (p.compareTo(node.point) == 0) return true; // might be slow? could adjust?

            if (orientation == 0) {
                if (Double.compare(p.x(), node.point.x()) == -1) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            } else {
                if (Double.compare(p.y(), node.point.y()) == -1) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);

        traverse(root, 1);
    }

    private void traverse(Node node, int orientation) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.point.x(), node.point.y());

        if (orientation == 1) orientation--;
        else orientation++;

        if (orientation == 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        traverse(node.left, orientation);
        traverse(node.right, orientation);
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
                        traverse(root);
                    }

                    @Override
                    public boolean hasNext() {
                        return !stack.isEmpty();
                    }

                    @Override
                    public Point2D next() {
                        if (!hasNext()) return null;
                        return stack.pop();
                    }

                    private void traverse(Node node) {
                        if (node == null) return;

                        if (rect.contains(node.point)) stack.push(node.point); //might be too heavy, could do simplier

                        if (node.left != null && rect.intersects(node.left.rect)) {
                            traverse(node.left);
                        }
                        if (node.right != null && rect.intersects(node.right.rect)) {
                            traverse(node.right);
                        }
                    }
                };
            }
        };

    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, root.point, p);
    }

    private Point2D nearest(Node node, Point2D best, Point2D query) {
        if (node == null) return best;

        if (node.point.distanceSquaredTo(query) < best.distanceSquaredTo(query)) best = node.point;

        if (node.left != null && node.left.rect.distanceSquaredTo(query) < best.distanceSquaredTo(query)) {
            best = nearest(node.left, best, query);
        }
        if (node.right != null && node.right.rect.distanceSquaredTo(query) < best.distanceSquaredTo(query)) {
            best = nearest(node.right, best, query);
        }


        return best;
    }

}