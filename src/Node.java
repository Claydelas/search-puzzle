import java.util.ArrayList;
import java.util.Collections;

public class Node {

    public State worldState;
    private int level;
    private int cost;

    public Node getParent() {
        return parent;
    }

    private Node parent;

    //When instantiating a Node, if just give a state,
    //then assume that it is the root node of a tree and set parent to null , node level to 0 and the cost to 0
    public Node(State worldState) {
        this.worldState = worldState;
        this.parent = null;
        this.level = 0;
        this.cost = 0;
    }
    public Node(State worldState, Node parent, int level, int cost) {
        this.worldState = worldState;
        this.parent = parent;
        this.level = level;
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public State getWorldState() {
        return worldState;
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