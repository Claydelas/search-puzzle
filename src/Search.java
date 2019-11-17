import java.util.*;

public class Search {


    private static long xy(int x, int y) {
        return (((long) x) << 32) | y;
    }

    public static void main(String[] args) {
        Node start = new Node(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 2));
        Node goal = new Node(4, 4, xy(0, 0), xy(3, 1), xy(3, 2), xy(3, 3));
        //Node goal = new Node(4, 4, xy(0, 0), xy(3, 1), xy(2, 1), xy(1, 1));
        //Node goal = new Node(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 3));
        bfs(start, goal, false);
        dfs(start, goal, false);
    }

    public static Node dfs(Node start, Node goal, boolean details) {
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
            if (isAtGoalState(current, goal, nodesExpanded, details)) return current;

            //expands current node
            nodesExpanded++;

            ArrayList<Node> successors = current.getPossibleMoves();
            //randomises expansion order
            Collections.shuffle(successors);

            //adds successors to the queue
            queue.addAll(successors);
        }
        return null;
    }

    public static Node bfs(Node start, Node goal, boolean details) {
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
            switch (Thread.currentThread().getStackTrace()[2].getMethodName()){
                case "bfs":
                    System.out.print("Breadth-First");
                    break;
                case "dfs":
                    System.out.print("Depth-First");
                    break;
                case "ids":
                    System.out.print("Iterative-Deepening");
                    break;
                case "aStar":
                    System.out.print("A*");
                    break;
            }
            System.out.println(" search found solution on depth: ["
                    + current.getLevel() + "] with [" + nodesExpanded + "] nodes expanded:");
            current.printWorld();

            return true;
        }
        return false;
    }

    public static void printSolution(Node current, boolean details) {
        System.out.println("Solution:");
        for (Node step : current.getPath()) {
            System.out.println(step.getLastMove());
            if (details) step.printWorld();
        }
    }
}
