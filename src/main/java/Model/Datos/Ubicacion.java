package Model.Datos;

public abstract class Ubicacion {

    protected String etiqueta;
    protected String nombre;
    protected double latitud;
    protected double longitud;

    public String getEtiqueta() {
        return etiqueta;
    }
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public abstract String getNombre();
    public abstract void setNombre(String nombre);

    public abstract double getLatitud();
    public abstract double getLongitud();
    public abstract void setLatitud(double latitud);
    public abstract void setLongitud(double longitud);
}