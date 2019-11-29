package Model.Datos;

import java.time.LocalDate;

public class DatosMeteorologia {

    private Ubicacion ubicacion;
    private LocalDate dia;
    private String tipoDia;
    private double temp;
    private double humedad;
    private double viento;

    public DatosMeteorologia(Ubicacion ubicacion, LocalDate dia, String tipoDia, double temp, double humedad, double viento) {
        this.ubicacion = ubicacion;
        this.dia = dia;
        this.tipoDia = tipoDia;
        this.temp = temp;
        this.humedad = humedad;
        this.viento = viento;
    }

    public Ubicacion getUbicacion() { return ubicacion; }
    public LocalDate getDia() { return dia; }
    public String getTipoDia() { return tipoDia; }
    public double getTemp() { return temp; }
    public double getHumedad() { return humedad; }
    public double getViento() { return viento; }

    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }
    public void setDia(LocalDate dia) { this.dia = dia; }
    public void setTipoDia(String tipoDia) { this.tipoDia = tipoDia; }
    public void setTemp(double temp) { this.temp = temp; }
    public void setHumedad(double humedad) { this.humedad = humedad; }
    public void setViento(double viento) { this.viento = viento; }

    @Override
    public String toString() {
        return "DatosMeteorologia{" +
                "ubicacion=" + ubicacion.toString() +
                ", dia=" + dia.toString() +
                ", tipoDia='" + tipoDia + '\'' +
                ", temp=" + temp +
                ", humedad=" + humedad +
                ", viento=" + viento +
                '}';
    }
}