public class Search {


    private static long xy(int x, int y) {
        return (((long) x) << 32) | y;
    }

    public static void main(String[] args) {
        Search search = new Search();
        State start = new State(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 2));
        State goal = new State(4, 4, xy(3, 0), xy(3, 1), xy(3, 2), xy(2, 3));
        new BreadthFirstSearch(new Node(start), goal);
    }


    /*public void BFS(Node root, World goal) {
        int nodesExpanded = 0;
        Queue<Node> queue = new LinkedList<Node>();

        System.out.println("Starting Breadth-First Search on:\n" + root.getState());
        queue.add(root);
        while (!queue.isEmpty()) {
            ArrayList<Node> fringe = new ArrayList<>();
            //Remove queue head
            Node current = queue.remove();
            //Check if the solution is found
            if (checkGoal(current.getState(), goal)) {
                ArrayList<Node> steps = current.sequence(current);
                System.out.println("Finished Breadth-First Search with depth - " + current.getCost() + " and nodes expanded - " + nodesExpanded + "\n" + current.getState() + "\nSteps:\n");
                for (Node step : steps) {
                    System.out.println(step.getState());
                }
                break;
            }
            //Expands Node if solution not found
            nodesExpanded++;

            //fringe.add moveup
            //fringe.add moveleft
            //fringe.add movedown
            //fringe.add moveright

            //Add successors to the queue
            for (Node child : fringe) {
                if (child != null) {
                    queue.add(child);
                }
            }
        }
    }

    private boolean checkGoal(World current, World goal) {
        if (current.getWorld().equals(goal.getWorld())) {
            return true;
        } else {
            return false;
        }
    }*/
}
