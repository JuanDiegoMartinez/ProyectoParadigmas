package View;

public class Tag {
    private boolean condicion;
    private String tag;

    public Tag(boolean condicion, String tag) {
        this.condicion = condicion;
        this.tag = tag;
    }

    public boolean isTag() {
        return condicion;
    }

    public String getTag() {
        return tag;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
