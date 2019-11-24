package Model;

import Model.Datos.DatosMeteorologia;

public class SistemaFacade {

    //private final DatosMeteorologia datos;

    //public SistemaFacade(DatosMeteorologia datos) {
    //   this.datos = datos;
    //}

    //Obtener el estado del tiempo de hoy en una ciudad
    public DatosMeteorologia obtenerTiempoHoyCiudad(String ciudad) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de hoy en unas coordenadas
    public DatosMeteorologia obtenerTiempoHoyCoordenadas(double latitud, double longitud) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de los próximos X días en una ciudad
    public DatosMeteorologia obtenerTiempoXdiasCiudad(String ciudad, int dias) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de los próximos X días en unas coordenadas
    public DatosMeteorologia obtenerTiempoXdiasCoordenadas(double latitud, double longitud, int dias) {
        throw new UnsupportedOperationException();
    }

}
