import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Node {

    Tile[][] world;
    Tile agent;
    int agentX;
    int agentY;
    private int level;
    private int cost;
    private String lastStep;
    private Node parent;

    //extensible root Node from parameters
    public Node(int row, int col, long agentPos, long... blocks) {
        world = new Tile[row][col];
        for (Tile[] tiles : world) Arrays.fill(tiles, new Tile(Tile.Type.EMPTY_TILE));

        this.agentX = x(agentPos);
        this.agentY = y(agentPos);
        agent = new Tile(Tile.Type.AGENT);
        world[agentX][agentY] = agent;

        char name = 'A';
        for (long l : blocks) {
            world[x(l)][y(l)] = new Tile(Tile.Type.BLOCK, String.valueOf(name));
            name++;
        }
        this.parent = null;
        this.level = 0;
        this.cost = 0;
    }

    //root Node from existing world config
    public Node(Tile[][] world) {
        this.world = world;
        this.parent = null;
        this.level = 0;
        this.cost = 0;
    }

    //helper constructor for generating successors via movement
    public Node(Tile[][] world, Node parent, int level, int cost, long agentPos, String lastStep) {
        this.agentX = x(agentPos);
        this.agentY = y(agentPos);
        this.world = world;
        this.parent = parent;
        this.level = level;
        this.cost = cost;
        this.lastStep = lastStep;
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
        switch (nextMove) {
            case 0:
                return moveUp();
            case 1:
                return moveDown();
            case 2:
                return moveLeft();
            case 3:
                return moveRight();
        }
        return null;
    }

    Node moveLeft() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+left");
        if (agentY - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY - 1];
            world[agentX][agentY - 1] = oldAgentPos;
            return new Node(world, this, level + 1, cost + 1, xy(agentX, agentY - 1), "+left");
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
    }

    Node moveRight() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+right");
        if (agentY + 1 < world[agentX].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY + 1];
            world[agentX][agentY + 1] = oldAgentPos;
            return new Node(world, this, level + 1, cost + 1, xy(agentX, agentY + 1), "+right");
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
    }

    Node moveDown() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+down");
        if (agentX + 1 < world[agentY].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX + 1][agentY];
            world[agentX + 1][agentY] = oldAgentPos;
            return new Node(world, this, level + 1, cost + 1, xy(agentX + 1, agentY), "+down");
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
    }

    Node moveUp() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+up");
        if (agentX - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX - 1][agentY];
            world[agentX - 1][agentY] = oldAgentPos;
            return new Node(world, this, level + 1, cost + 1, xy(agentX - 1, agentY), "+up");
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
    }

    void printWorld() {
        for (Tile[] tiles : world) {
            Arrays.asList(tiles).forEach(tile -> System.out.print(tile.getLabel()));
            System.out.println();
        }
    }

    Tile[][] cloneWorld(Tile[][] world) {
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

}