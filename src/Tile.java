public class Tile {

    Type type;
    String label;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!Tile.class.isAssignableFrom(obj.getClass())) return false;
        final Tile other = (Tile) obj;
        return this.label.equals(other.label) && this.type.equals(other.type);
    }

    enum Type {
        AGENT,
        EMPTY_TILE,
        BLOCK,
    }
}
