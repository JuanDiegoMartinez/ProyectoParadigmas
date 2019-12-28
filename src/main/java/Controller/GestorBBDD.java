package Controller;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// Clase encargada de realizar las peticiones y convertir el resultado de las peticiones en datos del modelo.
// Utiliza la clase JSONParser para procesar los Strings recibidos.
public class GestorBBDD {

    public static DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) {
        throw new NotImplementedException();
    }

    public static DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) {
        throw new NotImplementedException();
    }

    public static DatosMeteorologia obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) {
        throw new NotImplementedException();
    }

    public static DatosMeteorologia obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) {
        throw new NotImplementedException();
    }
}
