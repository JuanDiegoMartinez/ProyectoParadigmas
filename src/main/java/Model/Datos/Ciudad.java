package Model.Datos;

public class Ciudad extends Ubicacion {

    private String nombre;

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public double getLatitud() {
        return 0;
    }

    @Override
    public double getLongitud() {
        return 0;
    }

    @Override
    public void setLatitud(double latitud) {

    }

    @Override
    public void setLongitud(double longitud) {

    }

    @Override
    public String toString() {
        return this.nombre;
    }
}