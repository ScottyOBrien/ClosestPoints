import java.util.*;
import java.io.*;

/**
 * @author Scott O'Brien
 * @version 3/20/2019
 *
 * Java program that finds the closest pair of points in a set Q of points.
 */

public class ClosestPoints {

    public static void main(String[] args) {

        //Get the file name from the user. Using scanner.
        Scanner inputFile = new Scanner(System.in);
        System.out.print("Please enter file name with extension (e.g. \"text.dat\"): ");
        String fileName = inputFile.nextLine();

        //File file;
        //Grab data from file using buffered reader, we'll adjust this later to put the points into their respective
        //arrays.
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String points = reader.readLine();

            while (points != null) {
                System.out.println(points);
                points = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
