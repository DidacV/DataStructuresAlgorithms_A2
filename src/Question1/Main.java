package Question1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Didac
 */
public class Main {
    
    /**
     * Finds the elements given in the assignment with each algorithm.
     * @param A given array in the assignment
     * @param elements given elements
     */
    public static void findGivenElements(int[][] A, int[] elements){
        for (int j = 0; j < elements.length; j++){
            int p = elements[j];
            for (int i = 0; i < 3; i++){
                switch(i){
                    case 0:
                        System.out.println("Finding " + p + " with Linear search");
                        System.out.println(findElementD(A, A.length, p));
                        break;
                    case 1:
                        System.out.println("Finding " + p + " with Binary search");
                        System.out.println(findElementD1(A, A.length, p));
                        break;
                    case 2:
                        System.out.println("Finding " + p + " with Step search");
                        System.out.println(findElementD2(A, A.length, p));
                        break;
                }
            }
        }
    }
    
    /**
     * Performs an accurate timing of the algorithms, outputs to console
     * and to a CSV file.
     * @param A 2D Array
     * @param n Size of the array
     * @param p Number to find
     * @param D The type of algorithm to be used (D(0), D(1), D(2))
     */
    public static void timingExperiment(int[][] A, int n, int p, int D){
        double[] data = new double[4];
        int reps = 1000;
        double sum = 0;
        double sumSquared = 0;
        switch (D){
            case 0:
                // Worst case for D, is when it's not in the array
                for (int i = 0; i < reps; i++){
                    long t1 = System.nanoTime();
                    findElementD(A, n, p);
                    long t2 = System.nanoTime() - t1;
                    sum += (double)t2/1000000.0;
                    sumSquared += (t2/1000000.0) * (t2/1000000.0);
                }
                break;
            case 1:
                // Worst case for D1, is when p is the first or last in the array
                for (int i = 0; i < reps; i++){
                    long t1 = System.nanoTime();
                    findElementD1(A, n, p);
                    long t2 = System.nanoTime() - t1;
                    sum += (double)t2/1000000.0;
                    sumSquared += (t2/1000000.0) * (t2/1000000.0);
                }
                break;
            case 2:
                // Worst case for D2 is when p is in the bottom left
                //A[0][n-1] = p;
                for (int i = 0; i < reps; i++){
                    long t1 = System.nanoTime();
                    findElementD2(A, n, p);
                    long t2 = System.nanoTime() - t1;
                    sum += (double)t2/1000000.0;
                    sumSquared += (t2/1000000.0) * (t2/1000000.0);
                }
                break;
        }
        double mean = sum/reps;
        double variance = sumSquared / reps - (mean*mean);
        double stdDev = Math.sqrt(variance);
        data[0] = n;
        data[1] = mean;
        data[2] = variance;
        data[3] = stdDev;
        System.out.format(mean + " \t|\t " + variance + " \t|\t " + stdDev + "\n");
        try {
            createCSV(data);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This will create a non-descending 2D random array, columns first (A[j][i])
     * To calculate the worst-case accurately for D2, this array has a property:
     * the integer at the bottom left corner won't be repeated anywhere else in
     * the array.
     * @param n the size of the array
     * @return 2D array
     */
    public static int[][] createMatrix(int n){
        Random r = new Random();
        int prevColVal = 0;
        int max = 1;
        int min = 1;
        int randomNo = r.nextInt(max - min + 1) + min;
        int nToExclude;
        //int originalRandomNo = randomNo;
        //int randomDecrease = r.nextInt(20 - 5 + 1) + 5;
        int[][] A = new int[n][n];
        for (int i = 0; i < n; i++){
            nToExclude = A[n-1][0];
            for (int j = 0; j < n; j++){
                // If there are previous columns
                if (i > 0){
                    // Get the value
                    //prevRowVal = A[0][j];
                    prevColVal = A[j][i-1];
                }
                int randomIncrease = r.nextInt(10);
                randomNo += randomIncrease;
                // Increase randomNo to be bigger than the previous value 
                // in column
                while (randomNo < prevColVal || randomNo == nToExclude){
                    randomIncrease = r.nextInt(10);
                    randomNo += randomIncrease;
                }
                // Set randomNo to this position
                A[j][i] = randomNo;
                //randomNo += randomIncrease;
            }
            min = A[0][i];
            max = A[0][i];
            //max = A[0][n-1];
            randomNo = r.nextInt(max - min + 1) + min;
            
        }
        return A;
    }
    
    /**
     * Creates an array sorted by row in a non-descending order. 
     * @param n size of the array
     * @param nToExclude number to exclude will be p (for worst case instance)
     * @return 2D array with sorted rows
     */
    public static int[][] createMatrixD1(int n, int nToExclude){
        Random r = new Random();
        int[][] A = new int[n][n];
        int prevColVal;
        int max = 1;
        int min = 1;
        for (int i = 0; i < n; i++){
            int randomNo = r.nextInt(max - min + 1) + min;
            prevColVal = 0;
            for (int j = 0; j < n; j++){
                if (j > 0) {prevColVal = A[i][j-1];}
                randomNo += r.nextInt(nToExclude);
                
                while (randomNo < prevColVal || randomNo <= nToExclude){
                    randomNo += r.nextInt(nToExclude);
                }
                
                A[i][j] = randomNo;
            }
            A[i][0] = r.nextInt(nToExclude);
        }
        return A;
    }
    
    public static boolean findElementD(int[][] A, int n, int p){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if(A[i][j] == p) return true;
            }
        }
        return false;
    }
    
    public static boolean findElementD1(int[][] A, int n, int p){
        //System.out.println("Finding... " + p);
        boolean found = false;
        for (int i = 0; i < n; i++){
            found = binary(A[i], 0, n, p);
            if (found) break;
        }
        return found;
    }
    
    public static boolean findElementD2(int[][] A, int n, int p){
        // Pivot will be top right
        int pivot = A[0][n-1];
        return step(A, pivot, 0, n-1, p);
    }
    
    /**
     * Implementation of step algorithm in recursive form. 
     * It assumes the 2D array is in non-descending order
     * @param A Non-descending sorted 2D array
     * @param pivot Position where to start the algorithm from (top right of 2D array)
     * @param row Starts at 0 and it increments when p > pivot
     * @param column Starts at n-1 and it decrements when p < pivot
     * @param p Number to find
     * @return true if p is found, false otherwise
     */
    public static boolean step(int[][] A, int pivot, int row, int column, int p){
//        if (p < A[row][0]  || p > A[0][A.length - 1]){
//            return false;
//        }
        // Check bounds
        //if (row == A.length - 1 && column == 0) return p == pivot;
        //System.out.println(pivot);
        if (p > pivot && row < A.length - 1){
            row++;
            pivot = A[row][column];
            return step(A, pivot, row, column, p);
        } else if (p < pivot && column > 0){
            column--;
            pivot = A[row][column];
            return step(A, pivot, row, column, p);
        } else {
            return pivot == p;
        }
    }
    
    /**
     * Implementation of binary search in recursive form.
     * @param row sorted row to find p in
     * @param left left side of row
     * @param right right side of row
     * @param p integer to find
     * @return true if number is found, false otherwise
     */
    public static boolean binary(int[] row, int left, int right, int p){
        // If p can't be in this row, check next one
        if (p < row[0]  || p > row[row.length - 1]){
            return false;
        }
        
        if (left > right) return false;
        
        int mid = (left + right) / 2;
        if (row[mid] == p){
            return true;
        } else if (p > row[mid]){
            return binary(row, mid + 1, right, p);
        } else {
            return binary(row, left, mid - 1, p);
        }
    }
    
    /**
     * Creates a CSV file
     * @param data
     * @throws IOException 
     */
    public static void createCSV(double[] data) throws IOException{
        FileWriter pw = new FileWriter("data.csv", true);
        StringBuilder sb = new StringBuilder();
        for (double t : data){
            sb.append(t).append(",");
        }
        sb.append("\n");
        pw.write(sb.toString());
        pw.flush();
        pw.close();
    }
    
    public static void printMatrix(int[][] A){
        StringBuilder sb = new StringBuilder();
        for (int[] row : A){
            sb.append(Arrays.toString(row)).append("\n");
        }
        
        System.out.println(sb.toString());
    }
    
    public static void main(String[] args){
        int p = 0;
        System.out.println("1 to find given numbers with given array, \n"
                + "2 to run test experiment with random arrays (worst case instances):");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        while (option != 1 && option != 2){
            System.out.println("Try again: \n");
            option = sc.nextInt();
        }
        
        if(option == 1){
            int[] elements = {4,12,110,5,6,111};
            int[][] A = {
                //0   1   2    3    4    5    6
                { 1,  3,  7,   8,   8,   9,  12}, // 0
                { 2,  4,  8,   9,  10,  30,  38}, // 1
                { 4,  5, 10,  20,  29,  50,  60}, // 2
                { 8, 10, 11,  30,  50,  60,  61}, // 3
                {11, 12, 40,  80,  90, 100, 111}, // 4
                {13 ,15, 50, 100, 110, 112, 120}, // 5
                {22, 27, 61, 112, 119, 138, 153}  // 6
            };
            findGivenElements(A, elements);
        } else {
            int[][] A = new int[0][0];
            for (int i = 0; i < 3; i++){
                switch(i){
                    case 0:
                        System.out.println("Testing with linear search (D):");
                        System.out.format("\t" + "MEAN" + " \t\t|\t " + "\t" + "VARIANCE" + " \t|\t " + "\t" + "STDDEV" + "\n");
                        break;
                    case 1:
                        System.out.println("Testing with binary search (D1):");
                        System.out.format("\t" + "MEAN" + " \t\t|\t " + "\t" + "VARIANCE" + " \t|\t " + "\t" + "STDDEV" + "\n");
                        break;
                    case 2:
                        System.out.println("Testing with step search (D2):");
                        System.out.format("\t" + "MEAN" + " \t\t|\t " + "\t" + "VARIANCE" + " \t|\t " + "\t" + "STDDEV" + "\n");
                        break;
                }
                for (int n = 0; n < 14000;){
                    if (n < 100){n += 10;} 
                    else if (n >= 100 && n < 1000){n += 100;} 
                    else {n += 1000;}
                    
                    switch(i){
                        case 0: A = createMatrix(n); p = -1; break;
                        case 1: p = 5; A = createMatrixD1(n,p);  break;
                        case 2: A = createMatrix(n); p = A[n-1][0]; break;
                    }
                    
                    timingExperiment(A, n, p, i);
                }
            }
        }
    }
}
