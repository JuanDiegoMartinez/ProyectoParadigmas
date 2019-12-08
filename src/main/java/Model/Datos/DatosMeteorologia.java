package Model.Datos;

import java.time.LocalDate;
import java.time.LocalTime;

public class DatosMeteorologia {

    private Ubicacion ubicacion;
    private LocalDate dia;
    private LocalTime hora;
    private String tipoDia;
    private double tempMin;
    private double tempMax;
    private double humedad;
    private double viento;

    public DatosMeteorologia() {}

    public DatosMeteorologia(Ubicacion ubicacion, LocalDate dia, LocalTime hora, String tipoDia,
                             double tempMin, double tempMax, double humedad, double viento) {
        this.ubicacion = ubicacion;
        this.dia = dia;
        this.hora = hora;
        this.tipoDia = tipoDia;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humedad = humedad;
        this.viento = viento;
    }

    public Ubicacion getUbicacion() { return ubicacion; }
    public LocalDate getDia() { return dia; }
    public LocalTime getHora() { return hora; }
    public String getTipoDia() { return tipoDia; }
    public double getTempMin() { return tempMin; }
    public double getTempMax() { return tempMax; }
    public double getHumedad() { return humedad; }
    public double getViento() { return viento; }

    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }
    public void setDia(LocalDate dia) { this.dia = dia; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setTipoDia(String tipoDia) { this.tipoDia = tipoDia; }
    public void setTempMin(double tempMin) { this.tempMin = tempMin; }
    public void setTempMax(double tempMax) { this.tempMax = tempMax; }
    public void setHumedad(double humedad) { this.humedad = humedad; }
    public void setViento(double viento) { this.viento = viento; }

    @Override
    public String toString() {
        return "DatosMeteorologia{" +
                "ubicacion=" + ubicacion +
                ", dia=" + dia +
                ", hora=" + hora +
                ", tipoDia='" + tipoDia + '\'' +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", humedad=" + humedad +
                ", viento=" + viento +
                '}';
    }
}