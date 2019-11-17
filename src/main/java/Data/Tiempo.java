package Data;

public class Tiempo {

    private String ubicacion;
    private String dia;
    private String tipo;
    private String temp;
    private String humedad;
    private String viento;

    public Tiempo(String ubicacion, String dia, String tipo, String temp, String humedad, String viento) {
        this.ubicacion = ubicacion;
        this.tipo = dia;
        this.dia = tipo;
        this.temp = temp;
        this.humedad = humedad;
        this.viento = viento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDia() {
        return dia;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumedad() {
        return humedad;
    }

    public String getViento() {
        return viento;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    @Override
    public String toString() {
        return "Tiempo{" +
                "ubicacion='" + ubicacion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", temp='" + temp + '\'' +
                ", humedad='" + humedad + '\'' +
                ", viento='" + viento + '\'' +
                '}';
    }
}
