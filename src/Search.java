import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public class Search {

    private static long xy(int x, int y) {
        return (((long) x) << 32) | y;
    }

    public static void main(String[] args) throws FileNotFoundException {

        //--------Spec-------------------
        Node start = new Node(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 2));
        Node goalSpec = new Node(start, xy(1, 1), xy(2, 1), xy(3, 1));
        runSpecTests(start, goalSpec, 1);
        runTestsNormal(start, 13, 1);
        //--------/w Blocked Tiles-------
        Node startBlocked = new Node(4, 4, xy(3, 3), new long[]{xy(0, 1), (xy(1, 3))}, xy(3, 0), xy(3, 1), xy(3, 2));
        runTestsBlocked(startBlocked, 14, 1);
        //--------NxN Grid---------------
        runTestsNGrid(6, 3, 1);
    }

    private static Node ids(Node start, Node goal, int detailsLevel) {
        int nodesExpanded = 0;
        int nodesGenerated = 0;
        int depthLimit = 0;
        Deque<Node> stack = new ArrayDeque<>();

        stack.add(start);
        while (!stack.isEmpty()) {
            Node current = stack.removeLast();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (isAtGoalState(current, goal, nodesExpanded, nodesGenerated, detailsLevel)) return current;

            if (current.getLevel() < depthLimit) {
                nodesExpanded++;
                ArrayList<Node> successors = current.getPossibleMoves();
                nodesGenerated += successors.size();

                //randomises expansion order
                Collections.shuffle(successors);

                stack.addAll(successors);
            }
            if (stack.isEmpty()) {
                stack.push(start);
                depthLimit++;
            }
        }
        return null;
    }

    private static Node aStar(Node start, Node goal, int detailsLevel) {
        int nodesExpanded = 0;
        int nodesGenerated = 0;
        Queue<Node> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(start);
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.remove();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (isAtGoalState(current, goal, nodesExpanded, nodesGenerated, detailsLevel)) return current;

            nodesExpanded++;
            ArrayList<Node> successors = current.getPossibleMoves();
            nodesGenerated += successors.size();

            successors.forEach(node -> node.heuristic(goal));

            priorityQueue.addAll(successors);
        }
        return null;
    }

    private static Map dfs(Node start, Node goal, int detailsLevel) {
        int nodesExpanded = 0;
        int nodesGenerated = 0;
        Deque<Node> stack = new ArrayDeque<>();

        stack.add(start);
        while (!stack.isEmpty()) {
            Node current = stack.removeLast();
            //frees up memory, since the nodes that remain to be expanded after popping
            //the last one can never be reached. depth-first search on the blocksworld
            //puzzle goes down a single branch until either a solution is found or out of memory
            stack.clear();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (isAtGoalState(current, goal, nodesExpanded, nodesGenerated, detailsLevel))
                return Map.of(current, nodesGenerated);

            nodesExpanded++;
            ArrayList<Node> successors = current.getPossibleMoves();
            nodesGenerated += successors.size();

            //randomises expansion order
            Collections.shuffle(successors);

            stack.addAll(successors);
        }
        return null;
    }

    private static Node bfs(Node start, Node goal, int detailsLevel) {
        int nodesExpanded = 0;
        int nodesGenerated = 0;
        Queue<Node> queue = new LinkedList<>();

        queue.add(start);
        while (!queue.isEmpty()) {
            Node current = queue.remove();

            //checks if @goal state, and if so, breaks the loop -> prints path.
            if (isAtGoalState(current, goal, nodesExpanded, nodesGenerated, detailsLevel)) return current;

            nodesExpanded++;
            ArrayList<Node> successors = current.getPossibleMoves();
            nodesGenerated += successors.size();

            queue.addAll(successors);
        }
        return null;
    }

    private static boolean isAtGoalState(Node current, Node goal, int nodesExpanded, int nodesGenerated, int detailsLevel) {
        if (Arrays.deepEquals(current.getWorld(), goal.getWorld())) {
            if (detailsLevel != 0) {
                switch (Thread.currentThread().getStackTrace()[2].getMethodName()) {
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
                        + current.getLevel() + "] with [" + nodesExpanded + "] nodes expanded out of ["
                        + nodesGenerated + "] generated:");
                if (detailsLevel == 1) {
                    printSolution(current, false);
                    current.printWorld();
                }
                if (detailsLevel == 2) printSolution(current, true);
            }
            return true;
        }
        return false;
    }

    private static void printSolution(Node current, boolean details) {
        System.out.print("Path -> ");
        for (Node step : current.getPath()) {
            System.out.print(step.getLastMove() + " ");
            if (details) {
                System.out.println();
                step.printWorld();
            }
        }
        System.out.println();
    }

    private static void averageDFS(Node start, Node goal, int iterations) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            results.addAll(dfs(start, goal, 1).values());
        }
        System.out.println(results.stream().mapToInt(Integer::intValue).summaryStatistics());
    }

    private static Node generateDifficulty(Node start, int difficulty) {
        Node temp = start;
        for (int i = 0; i < difficulty; i++) {
            temp = temp.moveRandom();
        }
        if (aStar(start, temp, 0).getLevel() != difficulty)
            return generateDifficulty(start, difficulty);
        return temp;
    }

    private static void runTestsNormal(Node start, int maxDifficulty, int detailsLevel) throws FileNotFoundException {
        PrintStream bfs = new PrintStream("./results/extra/bfs.txt");
        PrintStream dfs = new PrintStream("./results/extra/dfs.txt");
        PrintStream ids = new PrintStream("./results/extra/ids.txt");
        PrintStream aStar = new PrintStream("./results/extra/aStar.txt");

        runTests(start, maxDifficulty, detailsLevel, bfs, dfs, ids, aStar);
    }

    private static void runTestsBlocked(Node start, int maxDifficulty, int detailsLevel) throws FileNotFoundException {
        PrintStream bfs = new PrintStream("./results/extra/blocked/bfs.txt");
        PrintStream dfs = new PrintStream("./results/extra/blocked/dfs.txt");
        PrintStream ids = new PrintStream("./results/extra/blocked/ids.txt");
        PrintStream aStar = new PrintStream("./results/extra/blocked/aStar.txt");

        runTests(start, maxDifficulty, detailsLevel, bfs, dfs, ids, aStar);
    }

    private static void runTestsNGrid(int upToN, int maxDifficulty, int detailsLevel) throws FileNotFoundException {
        PrintStream bfs = new PrintStream(new FileOutputStream("./results/extra/largerGrid/bfs.txt", true));
        PrintStream dfs = new PrintStream(new FileOutputStream("./results/extra/largerGrid/dfs.txt", true));
        PrintStream ids = new PrintStream(new FileOutputStream("./results/extra/largerGrid/ids.txt", true));
        PrintStream aStar = new PrintStream(new FileOutputStream("./results/extra/largerGrid/aStar.txt", true));

        for (int i = 5; i <= upToN; i++) {
            Node start = new Node(i, i, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 2));
            runTests(start, maxDifficulty, detailsLevel, bfs, dfs, ids, aStar);
        }
    }

    private static void runTests(Node start, int maxDifficulty, int detailsLevel,
                                 PrintStream bfs, PrintStream dfs, PrintStream ids, PrintStream aStar) {
        for (int i = 1; i <= maxDifficulty; i++) {

            Node goal = generateDifficulty(start, i);

            System.setOut(bfs);
            System.out.println("Difficulty " + i);
            bfs(start, goal, detailsLevel);
            System.out.println();

            System.setOut(dfs);
            System.out.println("Difficulty " + i);
            dfs(start, goal, detailsLevel);
            System.out.println();

            System.setOut(ids);
            System.out.println("Difficulty " + i);
            ids(start, goal, detailsLevel);
            System.out.println();

            System.setOut(aStar);
            System.out.println("Difficulty " + i);
            aStar(start, goal, detailsLevel);
            System.out.println();
        }
    }

    private static void runSpecTests(Node start, Node goal, int detailsLevel) throws FileNotFoundException {
        System.setOut(new PrintStream("./results/spec/bfs.txt"));
        bfs(start, goal, detailsLevel);
        System.gc();
        System.setOut(new PrintStream("./results/spec/dfs.txt"));
        averageDFS(start, goal, 50);
        System.gc();
        System.setOut(new PrintStream("./results/spec/ids.txt"));
        ids(start, goal, detailsLevel);
        System.gc();
        System.setOut(new PrintStream("./results/spec/aStar.txt"));
        aStar(start, goal, detailsLevel);
    }
}
