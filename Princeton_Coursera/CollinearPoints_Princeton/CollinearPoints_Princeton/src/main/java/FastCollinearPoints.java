import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int segmentCount;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        //Corner cases check
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) if (points[i] == null) throw new IllegalArgumentException();

        Point[] pointsCopy = points.clone();
        Point orgin;
        segmentCount = 0;
        LineSegment[] tempSegments = new LineSegment[points.length];
        Arrays.sort(pointsCopy);
        Point[] naturalCopy = pointsCopy.clone();
        double testSlope;

        for (int i = 0; i < points.length - 1; i++){
            if (points[i] == points[i + 1]) throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++){
            orgin = naturalCopy[i];

            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, orgin.slopeOrder());

            int j = 0;
            int connected = 0;

            while (j < points.length - 2){
                testSlope = orgin.slopeTo(pointsCopy[j]);
                connected = 1;
                while (j + connected < points.length && testSlope == orgin.slopeTo(pointsCopy[j + connected])){
                    connected++;
                }
                connected--;

                if (connected >= 2){
                    if (orgin.compareTo(pointsCopy[j]) < 0 && orgin.compareTo(pointsCopy[j + connected]) < 0){
                        tempSegments[segmentCount++] = new LineSegment(orgin, pointsCopy[j + connected]);
                    }
                    j = j + connected;
                }
                j++;
            }
        }

        segments = new LineSegment[segmentCount];
        for (int i = 0; i < segmentCount; i++){
            segments[i] = tempSegments[i];
        }
    }

    // the number of line segments
    public int numberOfSegments(){
        return segmentCount;
    }

    //the line segments
    public LineSegment[] segments(){
        return segments;
    }

}
