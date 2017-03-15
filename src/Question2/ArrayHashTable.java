package Question2;

/**
 *
 * @author Didac
 */
public class ArrayHashTable extends HashTable {

    int chainSize = 5;
    int[] counts = new int[capacity];
    Object[][] table = new Object[capacity][];
    
    public ArrayHashTable(){
        for (int i = 0; i < capacity; i++){
            table[i] = null;
            counts[i] = 0;
        }
    }
    
    @Override
    boolean add(Object obj) {
        int pos = obj.hashCode();
        if (table[pos] == null){
            Object[] chain = new Object[chainSize];
            // Add obj to end of chain
            chain[chainSize - 1] = obj;
            // Add chain in pos
            table[pos] = chain;
        } else {
            // add to correct position if it's not empty
            //if (table[pos] != )
            table[pos][0] = obj;
        }
        return false;
    }

    @Override
    boolean contains(Object obj) {
        return false;
    }

    @Override
    boolean remove(Object obj) {
        return false;
    }
    
    public static void main(String[] args){
        ArrayHashTable ht = new ArrayHashTable();
        ht.add(1);
    }
    
}
