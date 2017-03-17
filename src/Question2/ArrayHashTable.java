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
        int pos = obj.hashCode() % capacity;
        boolean added = false;
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
            System.out.println("New chain created");
            return true;
        } else {
            // if full
            if (counts[pos] == table[pos].length){
                // if it's full
                Object[] newChain = new Object[table[pos].length * 2];
                // copy values over
                System.arraycopy(table[pos], 0, newChain, 0, table[pos].length);
                table[pos] = newChain;
                // add obj
                table[pos][counts[pos]] = obj;
                // update count
                counts[pos]++;
                System.out.println("Chain full, created a new one and added obj");
                return true;
            } else {
                // if there isnt a duplicate
                if (!contains(obj)){
                    // add it in the null position
                    table[pos][counts[pos]] = obj;
                    counts[pos]++;
                    System.out.println("Added");
                    return true;
                }
            }
            System.out.println("Duplicate, can't add.");
            return false;
        }
    }

    @Override
    boolean contains(Object obj) {
        int pos = obj.hashCode() % capacity;
        boolean found = false;
        for (Object e : table[pos]) {
            if (e != null){
                // if it's a duplicate
                if(e.equals(obj)) found = true;
            }
        }
        return found;
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
                    table[pos][j+1] = null;
                }
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args){
        ArrayHashTable ht = new ArrayHashTable();
        System.out.println(ht.add(1));
        System.out.println(ht.add(1));
        System.out.println(ht.add(10));
        System.out.println(ht.add(20));
        System.out.println(ht.add(30));
        System.out.println(ht.add(40));
        System.out.println(ht.add(50));
        System.out.println(ht.add(60));
        System.out.println(ht.add(2));
        System.out.println(ht.add(2));
        System.out.println(ht.remove(10));
    }
    
}
