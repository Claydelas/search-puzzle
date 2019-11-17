import java.util.*;

public class DepthFirstSearch {

    public DepthFirstSearch(Node start, Node goal) {
        int nodesExpanded = 0;
        Deque<Node> queue = new ArrayDeque<>();

        System.out.println("Starting state:");
        start.printWorld();

        queue.add(start);
        while (!queue.isEmpty()) {

            Node current = queue.removeLast();
            //frees up memory, since the nodes that remain to be expanded after popping
            //the last one can never be reached. depth-first search on the blocksworld
            //puzzle goes down a single branch until either a solution is found or out of memory
            queue.clear();

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

            ArrayList<Node> successors = current.getPossibleMoves();

            //randomises expansion order
            Collections.shuffle(successors);

            //adds successors to the queue
            queue.addAll(successors);
        }
    }
}
