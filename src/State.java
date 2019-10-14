import java.util.Arrays;
import java.util.Random;

public class State {

    Tile[][] world;
    Tile agent;
    long agentPos;

    public State(Tile[][] world){
        this.world = world;
    }

    public State(int row, int col, long agentPos, long... blocks) {
        world = new Tile[row][col];
        for (Tile[] tiles : world) Arrays.fill(tiles, new Tile(Tile.Type.EMPTY_TILE));

        this.agentPos = agentPos;
        agent = new Tile(Tile.Type.AGENT);
        world[x(agentPos)][y(agentPos)] = agent;

        char name = 'A';
        for (long l : blocks) {
            world[x(l)][y(l)] = new Tile(Tile.Type.BLOCK, String.valueOf(name));
            name++;
        }

        showWorldState();
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

    void moveRandom() {
        int nextMove = new Random().nextInt(4);
        switch (nextMove) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }
    }

    State moveLeft() {
        State newState =  new State();
        System.out.print("+left");
        int agentX = x(agentPos);
        int agentY = y(agentPos);
        if (agentY - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY - 1];
            world[agentX][agentY - 1] = oldAgentPos;
            agentPos = xy(agentX, agentY - 1);
            System.out.println();
        } else System.out.println(" => out of bounds!");
        return this;
    }

    State moveRight() {
        System.out.print("+right");
        int agentX = x(agentPos);
        int agentY = y(agentPos);
        if (agentY + 1 < world[agentX].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY + 1];
            world[agentX][agentY + 1] = oldAgentPos;
            agentPos = xy(agentX, agentY + 1);
            System.out.println();
        } else System.out.println(" => out of bounds!");
        return this;
    }

    State moveDown() {
        System.out.print("+down");
        int agentX = x(agentPos);
        int agentY = y(agentPos);
        if (agentX + 1 < world[agentY].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX + 1][agentY];
            world[agentX + 1][agentY] = oldAgentPos;
            agentPos = xy(agentX + 1, agentY);
            System.out.println();
        } else System.out.println(" => out of bounds!");
        return this;
    }

    State moveUp() {
        System.out.print("+up");
        int agentX = x(agentPos);
        int agentY = y(agentPos);
        if (agentX - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX - 1][agentY];
            world[agentX - 1][agentY] = oldAgentPos;
            agentPos = xy(agentX - 1, agentY);
            System.out.println();
        } else System.out.println(" => out of bounds!");
        return this;
    }

    void showWorldState() {
        for (Tile[] tiles : world) {
            Arrays.asList(tiles).forEach(tile -> System.out.print(tile.label));
            System.out.println();
        }
    }
}
