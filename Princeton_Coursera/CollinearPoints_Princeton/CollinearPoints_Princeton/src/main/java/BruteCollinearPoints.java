import java.util.Arrays;

public class BruteCollinearPoints {
    private int segmentCount;
    private final LineSegment[] segments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        //Corner cases check
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            if (i < points.length - 1 && points[i] == points[i + 1]) throw new IllegalArgumentException();
        }

        Point[] pointCopy = points.clone();
        LineSegment[] tempSegments = new LineSegment[pointCopy.length];
        this.segmentCount = 0;
        Arrays.sort(pointCopy);



        for (int i = 0; i < pointCopy.length; i++) {
            if (i + 3 >= pointCopy.length) break;
            for (int j = i + 1; j < pointCopy.length; j++) {
                for (int k = j + 1; k < pointCopy.length; k++) {
                    for (int l = k + 1; l < pointCopy.length; l++) {
                        Point p = pointCopy[i];
                        Point q = pointCopy[j];
                        Point r = pointCopy[k];
                        Point s = pointCopy[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            tempSegments[segmentCount++] = new LineSegment(p, s);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[segmentCount];
        for (int i = 0; i < this.segmentCount; i++){
            segments[i] = tempSegments[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.segmentCount;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments;
    }

    public static void main(String[] args) {

    }

}