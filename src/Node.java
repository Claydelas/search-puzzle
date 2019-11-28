import java.util.*;

public class Node implements Comparable {

    private Tile[][] world;
    private int agentX;
    private int agentY;
    private int level;
    private int cost;
    private String lastStep;
    private Node parent;

    //goal state constructor
    public Node(Node start, long... blocks) {
        this.world = cloneWorld(start.world);
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                if (world[i][j].getType() == Tile.Type.AGENT || world[i][j].getType() == Tile.Type.BLOCK)
                    world[i][j] = new Tile(Tile.Type.EMPTY_TILE);
            }
        }
        char name = 'A';
        for (long l : blocks) {
            world[x(l)][y(l)] = new Tile(Tile.Type.BLOCK, String.valueOf(name));
            name++;
        }
    }

    //extensible root Node from parameters
    public Node(int row, int col, long agentPos, long... blocks) {
        world = new Tile[row][col];
        for (Tile[] tiles : world) Arrays.fill(tiles, new Tile(Tile.Type.EMPTY_TILE));

        this.agentX = x(agentPos);
        this.agentY = y(agentPos);
        world[agentX][agentY] = new Tile(Tile.Type.AGENT);

        char name = 'A';
        for (long l : blocks) {
            world[x(l)][y(l)] = new Tile(Tile.Type.BLOCK, String.valueOf(name));
            name++;
        }
    }

    public Node(int row, int col, long agentPos, long[] blocked, long... blocks) {
        world = new Tile[row][col];
        for (Tile[] tiles : world) Arrays.fill(tiles, new Tile(Tile.Type.EMPTY_TILE));

        this.agentX = x(agentPos);
        this.agentY = y(agentPos);
        world[agentX][agentY] = new Tile(Tile.Type.AGENT);

        for (long l : blocked) world[x(l)][y(l)] = new Tile(Tile.Type.BLOCKED_TILE);

        char name = 'A';
        for (long l : blocks) {
            world[x(l)][y(l)] = new Tile(Tile.Type.BLOCK, String.valueOf(name));
            name++;
        }
    }

    public Node(Tile[][] world, Node parent, long agentPos, String lastStep) {
        this.world = world;
        this.lastStep = lastStep;
        this.level = parent.getLevel() + 1;
        this.cost = parent.getCost() + 1;
        this.parent = parent;
        this.agentX = x(agentPos);
        this.agentY = y(agentPos);
    }

    private static long xy(int x, int y) {
        return (((long) x) << 32) | y;
    }

    private static int x(long xy) {
        return (int) (xy >> 32);
    }

    private static int y(long xy) {
        return (int) xy;
    }

    public Tile[][] getWorld() {
        return world;
    }

    Node moveRandom() {
        int nextMove = new Random().nextInt(4);
        Node temp;
        if (lastStep != null) {
            switch (nextMove) {
                case 0:
                    if ((temp = this.moveUp()) != this && !lastStep.equals("DOWN"))
                        return temp;
                case 1:
                    if ((temp = this.moveDown()) != this && !lastStep.equals("UP"))
                        return temp;
                case 2:
                    if ((temp = this.moveLeft()) != this && !lastStep.equals("RIGHT"))
                        return temp;
                case 3:
                    if ((temp = this.moveRight()) != this && !lastStep.equals("LEFT"))
                        return temp;
            }
        }
        return moveLeft();
    }

    private Node moveLeft() {
        if (agentY - 1 >= 0 && !world[agentX][agentY - 1].isBlocked()) {
            Tile[][] world = cloneWorld(this.world);
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY - 1];
            world[agentX][agentY - 1] = oldAgentPos;
            return new Node(world, this, xy(agentX, agentY - 1), "LEFT");
        }
        return this;
    }

    private Node moveRight() {
        if (agentY + 1 < world[agentX].length && !world[agentX][agentY + 1].isBlocked()) {
            Tile[][] world = cloneWorld(this.world);
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY + 1];
            world[agentX][agentY + 1] = oldAgentPos;
            return new Node(world, this, xy(agentX, agentY + 1), "RIGHT");
        }
        return this;
    }

    private Node moveDown() {
        if (agentX + 1 < world[agentY].length && !world[agentX + 1][agentY].isBlocked()) {
            Tile[][] world = cloneWorld(this.world);
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX + 1][agentY];
            world[agentX + 1][agentY] = oldAgentPos;
            return new Node(world, this, xy(agentX + 1, agentY), "DOWN");
        }
        return this;
    }

    private Node moveUp() {
        if (agentX - 1 >= 0 && !world[agentX - 1][agentY].isBlocked()) {
            Tile[][] world = cloneWorld(this.world);
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX - 1][agentY];
            world[agentX - 1][agentY] = oldAgentPos;
            return new Node(world, this, xy(agentX - 1, agentY), "UP");
        }
        return this;
    }

    void printWorld() {
        for (Tile[] tiles : world) {
            Arrays.asList(tiles).forEach(tile -> System.out.print(tile.getLabel()));
            System.out.println();
        }
    }

    private Tile[][] cloneWorld(Tile[][] world) {
        return Arrays.stream(world).map(Tile[]::clone).toArray(Tile[][]::new);
    }

    public String getLastMove() {
        return lastStep;
    }

    public Node getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<Node> getPath() {
        ArrayList<Node> sequence = new ArrayList<>();
        Node node = this;
        while (node.parent != null) {
            sequence.add(node);
            node = node.parent;
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public ArrayList<Node> getPossibleMoves() {
        ArrayList<Node> moves = new ArrayList<>();
        Node temp;
        if ((temp = this.moveUp()) != this) moves.add(temp);
        if ((temp = this.moveLeft()) != this) moves.add(temp);
        if ((temp = this.moveDown()) != this) moves.add(temp);
        if ((temp = this.moveRight()) != this) moves.add(temp);
        return moves;
    }

    public void heuristic(Node goal) {
        HashMap<String, Long> startMap = new HashMap<>();
        for (int x = 0; x < this.getWorld().length; x++) {
            for (int y = 0; y < this.getWorld()[x].length; y++) {
                if (this.getWorld()[x][y].getType().equals(Tile.Type.BLOCK))
                    startMap.put(this.getWorld()[x][y].getLabel(), xy(x, y));
            }
        }
        HashMap<String, Long> goalMap = new HashMap<>();
        for (int x = 0; x < goal.getWorld().length; x++) {
            for (int y = 0; y < goal.getWorld()[x].length; y++) {
                if (goal.getWorld()[x][y].getType().equals(Tile.Type.BLOCK))
                    goalMap.put(goal.getWorld()[x][y].getLabel(), xy(x, y));
            }
        }
        int cost = 0;
        for (Map.Entry<String, Long> entry : startMap.entrySet()) {
            //calculates the absolute distance from each Tile to the goal state
            cost += (Math.abs(x(entry.getValue()) - x(goalMap.get(entry.getKey()))) + Math.abs(y(entry.getValue()) - y(goalMap.get(entry.getKey()))));
            //ensures that nodes further up in the tree are explored first
            cost += level;
            //adjust the global cost variable for the node
            this.cost = cost;
        }
    }

    @Override
    public int compareTo(Object n) {
        Node tmp = (Node) n;
        return this.getCost() - tmp.getCost();
    }
}