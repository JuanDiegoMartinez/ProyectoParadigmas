package Controller;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.PeticionesServidor;

import java.util.List;

// Clase encargada de realizar las peticiones y convertir el resultado de las peticiones en datos del modelo.
// Utiliza la clase JSONParser para procesar los Strings recibidos.
public class GestorPeticiones {

    public static DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) {
        String resultado = PeticionesServidor.peticionCiudadHoy(ciudad);
        return JSONParser.convertirCiudadUnica(resultado);
    }

    public static DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) {
        String resultado = PeticionesServidor.peticionCoordenadasHoy(coordenadas);
        return JSONParser.convertirCiudadUnica(resultado);
    }

    public static List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) {
        if(dias > 5)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 5.");
        String resultado = PeticionesServidor.peticionCiudad5Dias(ciudad);
        List<DatosMeteorologia> datosParser = JSONParser.convertirListaCiudades(resultado);
        return datosParser.subList(0, dias-1);
    }

    public static List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) {
        if(dias > 5)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 5.");
        String resultado = PeticionesServidor.peticionCoordenadas5Dias(coordenadas);
        List<DatosMeteorologia> datosParser = JSONParser.convertirListaCiudades(resultado);
        return datosParser.subList(0, dias-1);
    }

}
