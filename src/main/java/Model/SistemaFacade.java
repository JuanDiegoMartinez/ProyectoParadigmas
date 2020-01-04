package Model;

import Controller.GestorBBDD;
import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.PeticionesFavoritos;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class SistemaFacade {

    //Obtener el estado del tiempo de hoy en una ciudad
    public static DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) throws LocationNotFoundException {
        DatosMeteorologia resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoHoyCiudad(ciudad);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
        return resultado;
    }

    //Obtener el estado del tiempo de hoy en unas coordenadas
    public static DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) throws LocationNotFoundException {
        DatosMeteorologia resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoHoyCoordenadas(coordenadas);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
        return resultado;
    }

    //Obtener el estado del tiempo de los próximos X días en una ciudad
    public static List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) throws LocationNotFoundException {
        List<DatosMeteorologia> resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoXdiasCiudad(ciudad, dias);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, dias);
        return resultado;
    }

    //Obtener el estado del tiempo de los próximos X días en unas coordenadas
    public static List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) throws LocationNotFoundException {
        List<DatosMeteorologia> resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoXdiasCoordenadas(coordenadas, dias);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoXdiasCoordenadas(coordenadas, dias);
        return resultado;
    }

    //Añadir una ciudad a la lista de favoritos
    public static boolean altaCiudadFavoritos(Ciudad ciudad) {
        return PeticionesFavoritos.anadirCiudad(ciudad);
    }

    //Borrar una ciudad de la lista de favoritos
    public static boolean bajaCiudadFavoritos(Ciudad ciudad) {
        return PeticionesFavoritos.borrarCiudad(ciudad);
    }

    //Añadir unas coordenadas a la lista de favoritos
    public static boolean altaCoordenadasFavoritos(Coordenadas coordenadas) {
        return PeticionesFavoritos.anadirCoordenadas(coordenadas);
    }

    //Borrar unas coordenadas a la lista de favoritos
    public static boolean bajaCoordenadasFavoritos(Coordenadas coordenadas) {
        return PeticionesFavoritos.borrarCoordenadas(coordenadas);
    }
}
