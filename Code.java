package myProjects;

import java.util.*;
import java.io.*;

public class Facial_Recognition_App {

    static Scanner userinput = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        // Store names
        String[] names = new String[5];

        // Measurements for 5 people (A-F)
        double[][] measure = new double[5][6];

        // Ratios for 5 people
        double[][] ratio = new double[5][15];

        // Read from file
        Scanner file = new Scanner(new File("Faces.txt"));

        for (int person = 0; person < 5; person++) {
            names[person] = file.next(); // person's name

            for (int m = 0; m < 6; m++) {
                measure[person][m] = file.nextDouble();
            }

            computeRatios(measure[person], ratio[person]);
        }

        file.close();

        // Mystery person input
        double[] mysteryMeasure = new double[6];
        double[] mysteryRatio = new double[15];

        System.out.println("Enter mystery person's 6 measurements:");

        for (int i = 0; i < 6; i++) {
            System.out.print("Measurement " + i + ": ");
            mysteryMeasure[i] = userinput.nextDouble();
        }

        computeRatios(mysteryMeasure, mysteryRatio);

        // Compare using sum of squares
        double smallest = Double.MAX_VALUE;
        int bestMatch = -1;

        for (int i = 0; i < 5; i++) {
            double sum = 0;

            for (int j = 0; j < 15; j++) {
                sum += Math.pow((mysteryRatio[j] - ratio[i][j]) / ratio[i][j], 2);
            }

            if (sum < smallest) {
                smallest = sum;
                bestMatch = i;
            }
        }

        // Output result
        System.out.println("\nThe mystery person is most likely: " + names[bestMatch]);
    }

    // Method to compute all 15 ratios
    public static void computeRatios(double[] m, double[] r) {

        r[0] = m[0] / m[1]; // A/B
        r[1] = m[0] / m[2]; // A/C
        r[2] = m[0] / m[3]; // A/D
        r[3] = m[0] / m[4]; // A/E
        r[4] = m[0] / m[5]; // A/F

        r[5] = m[1] / m[2]; // B/C
        r[6] = m[1] / m[3]; // B/D
        r[7] = m[1] / m[4]; // B/E
        r[8] = m[1] / m[5]; // B/F

        r[9] = m[2] / m[3]; // C/D
        r[10] = m[2] / m[4]; // C/E
        r[11] = m[2] / m[5]; // C/F

        r[12] = m[3] / m[4]; // D/E
        r[13] = m[3] / m[5]; // D/F

        r[14] = m[4] / m[5]; // E/F
    }
}
