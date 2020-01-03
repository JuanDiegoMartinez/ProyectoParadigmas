package Model;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SistemaFacade {

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

    //Añadir una ciudad a la lista de favoritos
    public static boolean altaCiudadFavoritos(Ciudad ciudad) {
        throw new NotImplementedException();
    }

    //Borrar una ciudad de la lista de favoritos
    public static boolean bajaCiudadFavoritos(Ciudad ciudad) {
        throw new NotImplementedException();
    }

    //Añadir unas coordenadas a la lista de favoritos
    public static boolean altaCoordenadasFavoritos(Coordenadas coordenadas) {
        throw new NotImplementedException();
    }

    //Borrar unas coordenadas a la lista de favoritos
    public static boolean bajaCoordenadasFavoritos(Coordenadas coordenadas) {
        throw new NotImplementedException();
    }
}
