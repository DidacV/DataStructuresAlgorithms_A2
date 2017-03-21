package Question2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Didac
 */
public class ArrayHashTable extends HashTable {

    int chainSize = 5;
    int[] counts = new int[capacity];
    Object[][] table = new Object[capacity][];
    
    
    //TODO: BST structure to enhance contains.
    // It'll make it O(log(n))
    //
    public ArrayHashTable(){
        for (int i = 0; i < capacity; i++){
            table[i] = null;
            counts[i] = 0;
        }
    }
    
    @Override
    boolean add(Object obj) {
        int pos = obj.hashCode() % capacity;
        // If entry is empty (null)
        if (table[pos] == null){
            // Create new array of size chainSize
            Object[] chain = new Object[chainSize];
            // Add obj to end of chain
            //chain[chainSize - 1] = obj;
            // Add chain in pos
            table[pos] = chain;
            // add obj to first element in chain
            chain[0] = obj;
            counts[pos]++;
            //System.out.println("New chain created");
            return true;
        } else {
            // if full
            if (counts[pos] == table[pos].length){
                // if it's full
                Object[] newChain = new Object[table[pos].length * 2];
                // copy values over
                System.arraycopy(table[pos], 0, newChain, 0, counts[pos]);
                table[pos] = newChain;
                // add obj
                table[pos][counts[pos]] = obj;
                // update count
                counts[pos]++;
                //System.out.println("Chain full, created a new one and added obj");
                return true;
            } else {
                // if there isnt a duplicate
                if (!contains(obj)){
                    // add it in the null position
                    table[pos][counts[pos]] = obj;
                    counts[pos]++;
                    //System.out.println("Added");
                    return true;
                }
            }
            //System.out.println("Duplicate, can't add.");
            return false;
        }
    }

    @Override
    boolean contains(Object obj) {
        int pos = obj.hashCode() % capacity;
        for (int i = 0; i < counts[pos] - 1; i++){
            //if(table[pos][counts[pos]] != null)
            if (table[pos][i].equals(obj)) return true;
        }
        return false;
    }

    @Override
    boolean remove(Object obj) {
        int pos = obj.hashCode() % capacity;
        if (counts[pos] >= 1){
            // if it's the first one but only one element in chain
//            if (table[pos][0].equals(obj) && counts[pos] == 1){
//                table[pos][0] = null;
//                counts[pos]--;
//                return true;
//            }
            // if it's the first or last
            if (table[pos][counts[pos]-1].equals(obj)){
                table[pos][counts[pos]-1] = null;
                counts[pos]--;
                return true;
            }
        }
        
        // if it's in the middle or beginning but more elements
        for (int i = 0; i < table[pos].length - 1; i++) {
            // if there's a duplicate
            if(table[pos][i].equals(obj)){
                table[pos][i] = null;
                counts[pos]--;
                // iterate throught the rest, shifting them up
                // and then break
                for (int j = i; j < table[pos].length - 1; j++){
                    table[pos][j] = table[pos][j+1];
                    if (table[pos][j+1] == null) break;
                    table[pos][j+1] = null;
                }
                return true;
            }
        }
        return false;
    }
    
//    private Object insertObjects(Comparable c, Object o){
//        int comp = c.compareTo(o);
//        if (comp == 0) {throw new IllegalArgumentException("Given element is there");}
//        else if (comp < 0) 
//    } 
    
    public static int[] createArray(int n){
        int[] A = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++){
            A[i] = Math.abs(r.nextInt());
        }
        return A;
    }
    
    public static void timingExperiment(int[] A, ArrayHashTable h, int n){
        double[] data = new double[4];
        int reps = 1000;
        double sum = 0;
        double sumSquared = 0;
        for (int i = 0; i < reps; i++){
            long t1 = System.nanoTime();
            for (int j = 0; j < n; j++){
                h.add(A[j]);
            }
            long t2 = System.nanoTime() - t1;
            sum += (double)t2/1000000.0;
            sumSquared += (t2/1000000.0) * (t2/1000000.0);
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
            //h.remove(34);
        } catch (IOException ex) {
            Logger.getLogger(ArrayHashTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createCSV(double[] data) throws IOException{
        FileWriter pw = new FileWriter("dataHashTable.csv", true);
        StringBuilder sb = new StringBuilder();
        for (double t : data){
            sb.append(t).append(",");
        }
        sb.append("\n");
        pw.write(sb.toString());
        pw.flush();
        pw.close();
    }
    
    public static void main(String[] args){
        ArrayHashTable h = new ArrayHashTable();
        HashSet hs = new HashSet();
        for (int n = 0; n < 50000;){
            if (n < 10000){n+=1000;}
            else {n+=5000;}
            int[] A = createArray(n);
            timingExperiment(A, h, n);
        }
        //timingExperiment(A, h, n);
    }
    
}
