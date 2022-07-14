package json;

public class Node {

    Pair<Object> pair = null;
    Node next = null;
    Node prev = null;

    public Node(Pair pair,Node prev){
        this.pair = pair;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return pair.toString();
    }
}
