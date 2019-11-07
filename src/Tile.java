public class Tile {

    private Type type;
    private String label;

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

    public boolean isAgent(){
        return this.type == Type.AGENT;
    }

    public Type getType(){
        return type;
    }

    public String getLabel(){
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!Tile.class.isAssignableFrom(obj.getClass())) return false;
        final Tile other = (Tile) obj;
        return this.label.equals(other.label) || this.type == Type.AGENT || other.type == Type.AGENT;
    }

    enum Type {
        AGENT,
        EMPTY_TILE,
        BLOCK,
    }
}
