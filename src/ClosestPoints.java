import java.util.*;
import java.io.*;

import static java.lang.Math.min;
import static java.lang.StrictMath.abs;

/**
 * @author Scott O'Brien
 * @version 3/20/2019
 *
 * Java program that finds the closest pair of points in a set Q of points.
 */

public class ClosestPoints {

    // declare an array of points that hold our points we create later with user data.
    public static Point[] xyPlane;
    // declare two auxiliary arrays we will use to help us with our divide and conquer alg.
    public static Point[] auxA;
    public static Point[] auxB;
    // initialize a variable to tell us how many lines of points we have (assuming we have 1 point per line, as
    // provided in the given points.dat files)
    public static int lines = 0;

    // declare some variables to help us keep track of the shortest distance and the 2 points for it so we can print at
    // the end of the program.
    public static double currentShortestDistance = 0;
    public static Point currentShortestX;
    public static Point currentShortestY;

    // here we create a basic point class with x,y variables. It also has a couple helper methods for use later.
    public static class Point implements Comparable<Point> {

        public double x, y;

        // Point() is a constructor that sets/init the x and y variables.
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // compareTo() allows us to compare x and y values for a given point.
        public int compareTo(Point p) {
            // compare this and p and there are three results: >0, ==0, or <0
            if (this.x == p.x) {
                if (this.y == p.y)
                    return 0;
                else
                    return (this.y > p.y)? 1 : -1;
            }
            else
                return (this.x > p.x)? 1 : -1;
        }

        // getDistance calculates the getDistance using the euclidean formula for the getDistance between two points.
        public double getDistance(Point p) {
            double dx = (this.x - p.x);
            double dy = (this.y - p.y);
            return Math.sqrt(dx*dx + dy*dy);
        }

        // toString is a to string method that prints the x and y values for a point.
        public String toString() {
            return " ("+ Double.toString(this.x) + "," + Double.toString(this.y) + ")";
        }


    }

    public static void main(String[] args) throws IOException {

        //Get the file name from the user. Using scanner.
        Scanner inputFile = new Scanner(System.in);
        System.out.print("Please enter file name with extension (e.g. \"text.dat\"): ");
        String fileName = inputFile.nextLine();

        // these 3 lines use a buffered reader to quickly get the amount of points in the file.
        BufferedReader lineCounter = new BufferedReader(new FileReader(fileName));
        while (lineCounter.readLine() != null) lines++;
        lineCounter.close();

        // initialize our 3 arrays of points to the size of how ever many points we have (given by the amount of lines)
        xyPlane = new Point[lines];
        auxB = new Point[lines];
        auxA = new Point[lines];

        //File file;
        //Grab data from file using buffered reader, we'll adjust this later to put the points into their respective
        //arrays. Also instantiate our arraylist of point values
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String points = reader.readLine();

            //Change this to put the points into arrays
            for (int i = 0; i < lines; i++) {
                String[] splitter = points.split(" ");
                double x = Double.parseDouble(splitter[0]);
                double y = Double.parseDouble(splitter[1]);
                xyPlane[i] = new Point(x, y);
                //System.out.println(xyPlane[i]);
                points = reader.readLine();
            }
            Arrays.sort(xyPlane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        double min1 = bruteForceMinDistance();
        double min2 = divideConquer(0, lines-1);
        System.out.println("The distance between the two closest points: ");
        System.out.println( currentShortestX.toString() + " & " + currentShortestY.toString() + "    distance: " + min2);
    }

    // method that helps us sort by y values, takes two numbers that are 2 positions in the array.
    static void swap(int i, int j) {
        Point x = xyPlane[i];
        xyPlane[i] = xyPlane[j];
        xyPlane[j] = x;
    }

    // A brute force method for computing the distance of the two closest points in the xyPlane[].
    static double bruteForceMinDistance() {
        double midDis = Double.POSITIVE_INFINITY;
        for (int i = 0; i < lines - 1; i++) {
            for (int j = i + 1; j < lines; j++) {
                if (xyPlane[i].getDistance(xyPlane[j]) < midDis){
                    midDis = xyPlane[i].getDistance(xyPlane[j]);
                }
            }
        }
        return midDis;

    }

    // method that performs the divide and conquer recursive algorithm. It takes in a low an high value that starts
    // at 0 and goes to the end of the amount of lines provided by the lines variable.
    static double divideConquer(int low, int high) {
        // Initialize some values we will need throughout this method.
        double minIntermediateDist;
        double minimum;
        double minDistance;

        if (high == low+1) { // when we are down to one pair of points after recursion
            if (xyPlane[low].y > xyPlane[high].y) swap(low, high);

            // here is where we store the current shortest distance, it will get updated overtime
            // and also store the 2 points for that given distance.
            if (currentShortestDistance == 0) {
                currentShortestDistance = xyPlane[low].getDistance(xyPlane[high]);
                currentShortestX = xyPlane[low];
                currentShortestY = xyPlane[high];
            }
            if (currentShortestDistance > xyPlane[low].getDistance(xyPlane[high])) {
                currentShortestDistance = xyPlane[low].getDistance(xyPlane[high]);
                currentShortestX = xyPlane[low];
                currentShortestY = xyPlane[high];
            }
            // here we return the distance calculation
            return xyPlane[low].getDistance(xyPlane[high]);
        }
        else if (high == low+2) { // three points
            // sort these points by y values.
            if (xyPlane[low].y > xyPlane[high].y) swap(low, high);
            if (xyPlane[low].y > xyPlane[low+1].y) swap(low, low+1);
            else if (xyPlane[low+1].y > xyPlane[high].y) swap(low+1, high);

            // Here we take the pairs of points and compute the getDistance between them using the getDistance method provided
            // in the Point() class above.
            double d1 = xyPlane[low].getDistance(xyPlane[high]);
            double d2 = xyPlane[low].getDistance(xyPlane[low+1]);
            double d3 = xyPlane[low+1].getDistance(xyPlane[high]);
            return ((d1 < d2)? ((d1 < d3)? d1 : d3) : (d2 < d3)? d2 : d3);  // return min(d1, d2, d3)
        } else {  // If we have 4 or more points then we switch to the divide and conquer algorithm with our recursive
                  // calls

            int mid = (high + low)/2;

            //Here we do the recursive calls to find our closest points
            double lowerPartMin = divideConquer(low,mid);
            double upperPartMin = divideConquer(mid+1,high);
            minIntermediateDist = min(lowerPartMin, upperPartMin);

            //some more variables to help us.
            int f = 0;
            double x0 = xyPlane[mid].x;

            for(int i = 1; i < lines; i++){
                if(abs(xyPlane[i].x-x0) <= minIntermediateDist){
                    f++;
                    auxA[f] = xyPlane[i];
                }
            }
            minimum = 2 * minIntermediateDist;
            for (int i = 1; i < f-1; i++){
                for(int j = i + 1; j < min(i+7,f);j++){
                    double distance0 = abs(auxA[i].getDistance(auxA[j]));
                    if(distance0 < minimum){
                        minimum = distance0;
                    }
                }
            }
            minDistance = min(minimum, minIntermediateDist);
        }
        return minDistance;
    }

}
