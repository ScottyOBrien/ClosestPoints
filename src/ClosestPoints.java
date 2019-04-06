import java.awt.*;
import java.util.*;
import java.io.*;

/**
 * @author Scott O'Brien
 * @version 3/20/2019
 *
 * Java program that finds the closest pair of points in a set Q of points.
 */

public class ClosestPoints {

    private ArrayList<Point> pointListX = new ArrayList<Point>();

    public static void main(String[] args) {

        //Get the file name from the user. Using scanner.
        Scanner inputFile = new Scanner(System.in);
        System.out.print("Please enter file name with extension (e.g. \"text.dat\"): ");
        String fileName = inputFile.nextLine();

        //File file;
        //Grab data from file using buffered reader, we'll adjust this later to put the points into their respective
        //arrays. Also instantiate our arraylist of point values
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String points = reader.readLine();
            double tempX = 0;
            double tempY = 0;

            //TODO: Change this to put the points into arrays
            while (points != null) {
                System.out.println(points);
                points = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Using this PointCompare() class we can create point objects and store them in a array, then call this method in
    // conjunction with Collections.sort() to sort our array by the X Values.
    public class PointCompare implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            }
            else if (a.x > b.x) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
