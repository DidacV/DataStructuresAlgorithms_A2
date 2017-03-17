package Question1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Didac
 */
public class Main {
    
    public static void main(String[] args){
        
//        int[][] A = {
//            //0   1   2    3    4    5    6
//            { 1,  3,  7,   8,   8,   9,  12}, // 0
//            { 2,  4,  8,   9,  10,  30,  38}, // 1
//            { 4,  5, 10,  20,  29,  50,  60}, // 2
//            { 8, 10, 11,  30,  50,  60,  61}, // 3
//            {11, 12, 40,  80,  90, 100, 111}, // 4
//            {13 ,15, 50, 100, 110, 112, 120}, // 5
//            {22, 27, 61, 112, 119, 138, 153}  // 6
//        };
        
        /*
        int[][] A = {
            //0   1   2    3    4    5    6
            { 1,  3,  7,   8,   8,   9,  12}, // 0
            { 1,  3,  7,   8,   8,   9,  12}, // 1
            { 1,  3,  7,   8,   8,   9,  12}, // 2
            { 1,  3,  7,   8,   8,   9,  12}, // 3
            { 1,  3,  7,   8,   8,   9,  12}, // 4
            { 1,  3,  7,   8,   8,   9,  12}, // 5
            { 1,  3,  7,   8,   8,   9,  13}  // 6
        };
        
        */
        int n = 6;
        int p = 10000;
        int[][] A = createA(n);
        //System.out.println(Exercise1.findElementD1(A, n, p));
//        System.out.println("D1:");
//        System.out.format("\t" + "MEAN" + " \t\t|\t " + "\t" + "VARIANCE" + " \t|\t " + "\t" + "STDDEV" + "\n");
//        for (int i = 0; i < 3; i++){
//            System.out.println("D" + i);
//            System.out.format("\t" + "MEAN" + " \t\t|\t " + "\t" + "VARIANCE" + " \t|\t " + "\t" + "STDDEV" + "\n");
//            for (int n = 0; n <= 4000;){
//                if (n < 100){
//                    n += 10;
//                } else if (n >= 100 && n < 1000){
//                    n += 100;
//                } else {
//                    n += 1000;
//                }
//                int[][] A = createA(n);
//                //findElementD2(A, n, p);
//                timingExperiment(A, n, p, i);
//            }
//        }
//findElementD2(A, n, p);
//findElementD1(A, n, p);
        //timingExperiment(A, n, p);
    }
    
    public static void timingExperiment(int[][] A, int n, int p, int D){
        double[] data = new double[4];
        int reps = 1000;
        double sum = 0;
        double sumSquared = 0;
        switch (D){
            case 0:
                for (int i = 0; i < reps; i++){
                    long t1 = System.nanoTime();
                    findElementD(A, n, p);
                    long t2 = System.nanoTime() - t1;
                    sum += (double)t2/1000000.0;
                    sumSquared += (t2/1000000.0) * (t2/1000000.0);
                }
                break;
            case 1:
                for (int i = 0; i < reps; i++){
                    long t1 = System.nanoTime();
                    findElementD1(A, n, p);
                    long t2 = System.nanoTime() - t1;
                    sum += (double)t2/1000000.0;
                    sumSquared += (t2/1000000.0) * (t2/1000000.0);
                }
                break;
            case 2:
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
    
    public static int[][] createA(int n){
        Random r = new Random();
        int prevColVal = 0;
        int max = 1;
        int min = 1;
        int randomNo = r.nextInt(max - min + 1) + min;
        //int originalRandomNo = randomNo;
        //int randomDecrease = r.nextInt(20 - 5 + 1) + 5;
        int[][] A = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // If there are previous rows
                if (i > 0){
                    // Get the value
                    prevColVal = A[i-1][j];
                }
                int randomIncrease = r.nextInt(6);
                randomNo += randomIncrease;
                // Increase randomNo to be bigger than the previous value 
                // in column
                while (randomNo < prevColVal){
                    randomIncrease = r.nextInt(16);
                    randomNo += randomIncrease;
                }
                // Set randomNo to this position
                A[i][j] = randomNo;
            }
            min = A[i][0];
            max = A[i][0];
            //max = A[0][n-1];
            randomNo = r.nextInt(max - min + 1) + min;
            //randomNo += randomIncrease;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int[] row : A){
            sb.append(Arrays.toString(row)).append("\n");
        }
        
        System.out.println(sb.toString());
        return A;
    }
    
    public static boolean findElementD(int[][] A, int n, int p){
        boolean found = false;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                found = A[i][j] == p;
            }
        }
        return found;
    }
    
    public static boolean findElementD1(int[][] A, int n, int p){
        //System.out.println("Finding... " + p);
        boolean found = false;
        for (int i = 0; i < n; i++){
            //found = iterationD1(A[i], p);
            found = recursionD1(A[i], 0, n, p);
            if (found) break;
        }
        return found;
    }
    
    public static boolean findElementD2(int[][] A, int n, int p){
        // Pivot will be top right
        int pivot = A[0][n-1];
        return recursionD2(A, pivot, 0, n-1, p);
    }
    
    public static boolean recursionD2(int[][] A, int pivot, int row, int column, int p){
//        if (p < A[row][0]  || p > A[0][A.length - 1]){
//            return false;
//        }
        
        // Check bounds
        if (row == A.length - 1 || column == 0) return p == pivot;
        
        if (p > pivot){
            row++;
            pivot = A[row][column];
            return recursionD2(A, pivot, row, column, p);
        } else if (p < pivot){
            column--;
            pivot = A[row][column];
            return recursionD2(A, pivot, row, column, p);
        } else {
            return p == pivot;
        }
    }
    
    public static boolean iterationD1(int[] row, int p){
        int middleIndex = row.length / 2;
        int middlePivot = row[middleIndex];
        
        boolean found = false;
        if (p < middlePivot){
            // Go left. Iterate from beginning to middlePivot
            for (int i = 0; i < middleIndex; i++){
                found = row[i] == p;
                if (found) break;
            }
        } else if (p > middlePivot){
            // If it's more but it's out of 
            // Go right. Iterate from middlePivot to end
            for (int i = middleIndex; i < row.length; i++){
                found = row[i] == p;
                if (found) break;
            }
        } else {
            // It's equal to middlePivot
            found = true;
        }
        return found;
    }
    
    public static boolean recursionD1(int[] row, int indexBegin, int indexEnd, int p){
        // If p can't be in this row, check next one
        if (p < row[0]  || p > row[row.length - 1]){
            return false;
        }
        
        if (indexBegin == indexEnd || indexEnd - indexBegin == 1){
            return p == row[indexBegin];
        }
        
        int middleIndex = (indexBegin + indexEnd) / 2;
        int pivot = row[middleIndex];
        if (p > pivot){
            return recursionD1(row, middleIndex, indexEnd, p);
        } else {
            return recursionD1(row, indexBegin, middleIndex, p);
        }
    }
    
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
    
}
