import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    public BreadthFirstSearch(Node start, Node goal) {
        int nodesExpanded = 0;
        Queue<Node> queue = new LinkedList<>();

        System.out.println("Starting state:");
        start.printWorld();

        queue.add(start);
        while (!queue.isEmpty()) {
            ArrayList<Node> successors = new ArrayList<>();

            Node current = queue.remove();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (Arrays.deepEquals(current.getWorld(), goal.getWorld())) {

                System.out.println("Solution:");
                for (Node step : current.getPath()) {
                    System.out.println(step.getLastMove());
                    step.printWorld();
                }

                System.out.println("Breadth-First search found solution on depth: ["
                        + current.getLevel() + "] with [" + nodesExpanded + "] nodes expanded:");
                current.printWorld();
                break;
            }
            //expands current node
            nodesExpanded++;
            Node temp;
            temp = current.moveUp();
            if(current != temp) successors.add(temp);
            temp = current.moveLeft();
            if(current != temp) successors.add(temp);
            temp = current.moveDown();
            if(current != temp) successors.add(temp);
            temp = current.moveRight();
            if(current != temp) successors.add(temp);

            //adds successors to the queue
            queue.addAll(successors);
        }
    }
}