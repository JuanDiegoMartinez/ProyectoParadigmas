package Model;

import Controller.GestorBBDD;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;

public class SistemaFacade {

    public SistemaFacade(GestorBBDD mock) {
    }

    //Obtener el estado del tiempo de hoy en una ciudad
    public static DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de hoy en unas coordenadas
    public static DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de los próximos X días en una ciudad
    public static DatosMeteorologia obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) {
        throw new UnsupportedOperationException();
    }

    //Obtener el estado del tiempo de los próximos X días en unas coordenadas
    public static DatosMeteorologia obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) {
        throw new UnsupportedOperationException();
    }

}
