import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * basic breadth first search in java
 */
public class BreadthFirstSearch {

    public BreadthFirstSearch(Node start, State goal) {
        int nodesExpanded = 0;
        Queue<Node> queue = new LinkedList<Node>();

        System.out.println("Starting Breadth-First Search on:\n");
        start.getState().showWorldState();
        queue.add(start);
        while (!queue.isEmpty()) {
            ArrayList<Node> successors = new ArrayList<>();
            //Remove queue head
            Node current = queue.remove();
            //Check if the solution is found
            if (Arrays.deepEquals(current.getState().getWorld(), goal.getWorld())) {
                ArrayList<Node> steps = current.sequence(current);
                System.out.println("Finished Breadth-First Search with depth : " + current.getCost() + " and nodes expanded : " + nodesExpanded);
                current.getState().showWorldState();
                System.out.println("Steps:");
                for (Node step : steps) {
                    System.out.println(step.getLastStep());
                    step.getState().showWorldState();
                }
                break;
            }
            //Expands Node if solution not found
            nodesExpanded++;
            successors.add(new Node(current.getState().moveUp(), current, current.getLevel() + 1, current.getCost() + 1, "+up"));
            successors.add(new Node(current.getState().moveLeft(), current, current.getLevel() + 1, current.getCost() + 1, "+left"));
            successors.add(new Node(current.getState().moveDown(), current, current.getLevel() + 1, current.getCost() + 1, "+down"));
            successors.add(new Node(current.getState().moveRight(), current, current.getLevel() + 1, current.getCost() + 1, "+right"));

            //Add successors to the queue
            for (Node child : successors) {
                if (child != null) {
                    queue.add(child);
                }
            }
        }
    }
}