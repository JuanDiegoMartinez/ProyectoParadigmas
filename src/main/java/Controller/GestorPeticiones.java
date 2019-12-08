package Controller;

import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Datos.Ubicacion;
import Model.Peticiones.PeticionesServidor;

import java.util.List;

// Clase encargada de realizar las peticiones y convertir el resultado de las peticiones en datos del modelo.
// Utiliza la clase JSONParser para procesar los Strings recibidos.
public class GestorPeticiones {

    public static DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) throws LocationNotFoundException {
        String outApi = PeticionesServidor.peticionCiudadHoy(ciudad);
        DatosMeteorologia resultado = JSONParser.convertirDatosUnicos(outApi);
        resultado.setUbicacion(ciudad);
        return resultado;
    }

    public static DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) throws LocationNotFoundException {
        String outApi = PeticionesServidor.peticionCoordenadasHoy(coordenadas);
        DatosMeteorologia resultado = JSONParser.convertirDatosUnicos(outApi);
        resultado.setUbicacion(coordenadas);
        return resultado;
    }

    public static List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) throws LocationNotFoundException {
        if(dias > 4 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");
        String outApi = PeticionesServidor.peticionCiudad5Dias(ciudad);
        return procesarOutLista(ciudad, outApi, dias);
    }

    public static List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) throws LocationNotFoundException {
        if(dias > 4 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");
        String outApi = PeticionesServidor.peticionCoordenadas5Dias(coordenadas);
        return procesarOutLista(coordenadas, outApi, dias);
    }

    private static List<DatosMeteorologia> procesarOutLista(Ubicacion ubicacion, String outApi, int dias) {
        List<DatosMeteorologia> datosParser = JSONParser.convertirListaDatos(outApi);
        for(DatosMeteorologia dm : datosParser)
            dm.setUbicacion(ubicacion);
        return datosParser.subList(0, dias*8);
    }

}
