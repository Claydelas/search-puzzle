import java.util.Arrays;
import java.util.Random;

public class State {

    Tile[][] world;
    Tile agent;
    int agentX;
    int agentY;

    public State(Tile[][] world, int agentX, int agentY) {
        this.world = cloneWorld(world);
        this.agentX = agentX;
        this.agentY = agentY;
    }

    public State(int row, int col, long agentPos, long... blocks) {
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
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+left");
        if (agentY - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY - 1];
            world[agentX][agentY - 1] = oldAgentPos;
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
        return new State(world, agentX, agentY - 1);
    }

    State moveRight() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+right");
        if (agentY + 1 < world[agentX].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX][agentY + 1];
            world[agentX][agentY + 1] = oldAgentPos;
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
        return new State(world, agentX, agentY + 1);
    }

    State moveDown() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+down");
        if (agentX + 1 < world[agentY].length) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX + 1][agentY];
            world[agentX + 1][agentY] = oldAgentPos;
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
        return new State(world, agentX + 1, agentY);
    }

    State moveUp() {
        Tile[][] world = cloneWorld(this.world);
        //System.out.print("+up");
        if (agentX - 1 >= 0) {
            Tile oldAgentPos = world[agentX][agentY];
            world[agentX][agentY] = world[agentX - 1][agentY];
            world[agentX - 1][agentY] = oldAgentPos;
            //System.out.println();
        } else {
            //System.out.println(" => out of bounds!");
            //showWorldState();
            return this;
        }
        //showWorldState(world);
        return new State(world, agentX - 1, agentY);
    }

    void showWorldState() {
        for (Tile[] tiles : world) {
            Arrays.asList(tiles).forEach(tile -> System.out.print(tile.getLabel()));
            System.out.println();
        }
    }

    void showWorldState(Tile[][] state) {
        for (Tile[] tiles : state) {
            Arrays.asList(tiles).forEach(tile -> System.out.print(tile.getLabel()));
            System.out.println();
        }
    }

    Tile[][] cloneWorld(Tile[][] world) {
        return Arrays.stream(world).map(Tile[]::clone).toArray(Tile[][]::new);
    }
}
