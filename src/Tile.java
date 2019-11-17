public class Tile {

    private Type type;
    private String label;

    //generic Tile constructor
    public Tile(Type type) {
        this(type,"null");
    }

    //BLOCK constructor
    public Tile(Type type, String label) {
        switch (type) {
            case AGENT:
                this.label = "[\u263A]";
                break;
            case EMPTY_TILE:
                this.label = "[ ]";
                break;
            case BLOCKED_TILE:
                this.label = "[\u2717]";
                break;
            case BLOCK:
                this.label = "[" + label + "]";
                break;
        }
        this.type = type;
    }

    public boolean isAgent() {
        return this.type == Type.AGENT;
    }

    public boolean isBlocked() {
        return this.type == Type.BLOCKED_TILE;
    }

    public Type getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    //used for comparing world states, ignores agent pos.
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!Tile.class.isAssignableFrom(obj.getClass())) return false;
        final Tile other = (Tile) obj;
        return this.label.equals(other.label)
                || (this.type == Type.AGENT && other.type == Type.EMPTY_TILE)
                || (this.type == Type.EMPTY_TILE && other.type == Type.AGENT);
    }

    enum Type {
        AGENT,
        EMPTY_TILE,
        BLOCK,
        BLOCKED_TILE
    }
}
