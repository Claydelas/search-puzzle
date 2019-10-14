public class Tile {

    Type type;
    String label;

    public Tile() {
        this.label = "[ ]";
        this.type = Type.EMPTY_TILE;
    }

    public Tile(Type type) {
        if (type == Type.AGENT)
            this.label = "[#]";
        if (type == Type.EMPTY_TILE)
            this.label = "[ ]";
        this.type = type;
    }

    public Tile(Type type, String label) {
        if (type == Type.AGENT)
            this.label = "[#]";
        if (type == Type.EMPTY_TILE)
            this.label = "[ ]";
        else
            this.label = "[" + label + "]";
        this.type = type;
    }

    enum Type {
        AGENT,
        EMPTY_TILE,
        BLOCK,
    }

}

class Agent extends Tile {
    public Agent() {
        super(Type.AGENT);
    }
    
}
