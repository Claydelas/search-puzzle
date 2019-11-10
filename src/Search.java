public class Search {


    private static long xy(int x, int y) {
        return (((long) x) << 32) | y;
    }

    public static void main(String[] args) {
        Node start = new Node(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 2));
        //Node goal = new Node(4, 4, xy(3, 3), xy(3, 1), xy(3, 2), xy(3, 3));
        Node goal = new Node(4, 4, xy(3, 3), xy(3, 0), xy(3, 1), xy(3, 3));
        new BreadthFirstSearch(start, goal);
        //new DepthFirstSearch(start, goal);
    }

}
