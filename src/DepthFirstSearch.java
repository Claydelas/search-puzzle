import java.util.*;

public class DepthFirstSearch {

    public DepthFirstSearch(Node start, Node goal) {
        int nodesExpanded = 0;
        Deque<Node> queue = new ArrayDeque<>();

        System.out.println("Starting state:");
        start.printWorld();

        queue.add(start);
        while (!queue.isEmpty()) {
            ArrayList<Node> successors = new ArrayList<>();

            Node current = queue.removeLast();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (Arrays.deepEquals(current.getWorld(), goal.getWorld())) {

                System.out.println("Solution:");
                for (Node step : current.getPath()) {
                    System.out.println(step.getLastMove());
                    step.printWorld();
                }

                System.out.println("Depth-First search found solution on depth: ["
                        + current.getLevel() + "] with [" + nodesExpanded + "] nodes expanded:");
                current.printWorld();
                break;
            }
            //expands current node
            nodesExpanded++;
            successors.add(current.moveUp());
            successors.add(current.moveLeft());
            successors.add(current.moveDown());
            successors.add(current.moveRight());

            //randomises expansion order
            Collections.shuffle(successors);

            //adds successors to the queue
            queue.addAll(successors);
        }
    }
}
