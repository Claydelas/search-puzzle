import java.util.ArrayList;
import java.util.Collections;

public class Node {

    public State state;
    private int level;
    private int cost;
    private String lastStep;
    private Node parent;

    //When instantiating a Node, if just give a state,
    //then assume that it is the root node of a tree and set parent to null , node level to 0 and the cost to 0
    public Node(State state) {
        this.state = state;
        this.parent = null;
        this.level = 0;
        this.cost = 0;
    }

    public Node(State state, Node parent, int level, int cost) {
        this.state = state;
        this.parent = parent;
        this.level = level;
        this.cost = cost;
    }

    public Node(State state, Node parent, int level, int cost, String lastStep) {
        this.state = state;
        this.parent = parent;
        this.level = level;
        this.cost = cost;
        this.lastStep = lastStep;
    }

    public String getLastStep() {
        return lastStep;
    }

    public Node getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public State getState() {
        return state;
    }

    public ArrayList<Node> sequence(Node nodeToFindPath) {
        ArrayList<Node> sequence = new ArrayList<>();
        Node node = nodeToFindPath;
        while (node.parent != null) {
            sequence.add(node);
            node = node.parent;
        }
        Collections.reverse(sequence);
        return sequence;
    }

}