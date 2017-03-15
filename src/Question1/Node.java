package Question1;

/**
 *
 * @author Didac
 */
public class Node {
//    public enum Weight {
//        ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
//        int weight;
//        private Weight(int weight){
//            this.weight = weight;
//        }
//        
//        public int getWeight(){
//            return this.weight;
//        }
//    } 
//    public enum Direction {
//        UP, LEFT, RIGHT, DOWN;
//    }
    
    int nodeValue;
    Node parent;
    Node previous;
    Node child;
    
    public Node(Node node, int value){
        nodeValue = value;
        
    }
    
    public void addChild(Node next, int value){
        
    }
    
    public void insert(Node node){
        
    }
}
