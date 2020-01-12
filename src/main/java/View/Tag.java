package View;

public class Tag {
    private int respuesta;
    private String tag;

    public Tag(int respuesta, String tag) {
        this.respuesta = respuesta;
        this.tag = tag;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public String getTag() {
        return tag;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
