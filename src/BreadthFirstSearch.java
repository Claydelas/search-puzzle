import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * basic breadth first search in java
 */
public class BreadthFirstSearch {

    public BreadthFirstSearch(Node start, State goal) {
        int nodesExpanded = 0;
        Queue<Node> queue = new LinkedList<Node>();

        System.out.println("Starting Breadth-First Search on:\n" + start.getWorldState());
        queue.add(start);
        while (!queue.isEmpty()) {
            ArrayList<Node> successors = new ArrayList<>();
            //Remove queue head
            Node current = queue.remove();
            //Check if the solution is found
            if (current.getWorldState().getWorld().equals(goal.getWorld())) {
                ArrayList<Node> steps = current.sequence(current);
                System.out.println("Finished Breadth-First Search with depth - " + current.getCost() + " and nodes expanded - " + nodesExpanded + "\n" + current.getWorldState() + "\nSteps:\n");
                for (Node step : steps) {
                    System.out.println(step.getWorldState());
                }
                break;
            }
            //Expands Node if solution not found
            nodesExpanded++;
            successors.add(new Node(new State(current.getWorldState().moveUp().getWorld()),current,current.getLevel()+1,current.getCost()+1));
            current.getWorldState().showWorldState();
            successors.add(new Node(new State(current.getWorldState().moveDown().getWorld()),current,current.getLevel()+1,current.getCost()+1));
            current.getWorldState().showWorldState();
            successors.add(new Node(new State(current.getWorldState().moveLeft().getWorld()),current,current.getLevel()+1,current.getCost()+1));
            current.getWorldState().showWorldState();
            successors.add(new Node(new State(current.getWorldState().moveRight().getWorld()),current,current.getLevel()+1,current.getCost()+1));
            current.getWorldState().showWorldState();
            //Add successors to the queue
            for (Node child : successors) {
                if (child != null) {
                    queue.add(child);
                }
            }
        }
    }
}