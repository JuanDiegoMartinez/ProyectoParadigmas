package Model.Datos;

public class Coordenadas {

    private String etiqueta;
    private double latitud;
    private double longitud;

    public Coordenadas(String etiqueta, double latitud, double longitud) {
        this.etiqueta = etiqueta;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Etiqueta: " + this.etiqueta + "\nLatitud: " + this.latitud + ", Longitud: " + this.longitud;
    }
}
