package Model.Peticiones;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Clase para comunicarse directamente con la base de datos
// Realiza la petición y devuelve el resultado como String sin procesar.
public class PeticionesBBDD {

    public static String bbddCiudadHoy(Ciudad ciudad) {
        throw new NotImplementedException();
    }

    public static String bbddCoordenadasHoy(Coordenadas coordenadas) {
        throw new NotImplementedException();
    }

    public static String bbddCiudadXdias(Ciudad ciudad, int dias) {
        throw new NotImplementedException();
    }

    public static String bbddCoordenadasXdias(Coordenadas coordenadas, int dias) {
        throw new NotImplementedException();
    }
}
