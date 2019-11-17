import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    public static Node search(Node start, Node goal, boolean details) {
        int nodesExpanded = 0;
        Queue<Node> queue = new LinkedList<>();

        System.out.println("Starting state:");
        start.printWorld();

        queue.add(start);
        while (!queue.isEmpty()) {

            Node current = queue.remove();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (isAtGoalState(current, goal, nodesExpanded, details)) return current;

            //expands current node
            nodesExpanded++;

            //adds successors to the queue
            queue.addAll(current.getPossibleMoves());
        }
        return null;
    }

    public static boolean isAtGoalState(Node current, Node goal, int nodesExpanded, boolean details) {
        if (Arrays.deepEquals(current.getWorld(), goal.getWorld())) {
            printSolution(current, details);

            System.out.println("Breadth-First search found solution on depth: ["
                    + current.getLevel() + "] with [" + nodesExpanded + "] nodes expanded:");
            current.printWorld();

            return true;
        }
        return false;
    }

    public static void printSolution(Node current, boolean details){
        System.out.println("Solution:");
        for (Node step : current.getPath()) {
            System.out.println(step.getLastMove());
            if (details) step.printWorld();
        }
    }
}