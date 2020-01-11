package Controller;

import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.InsertarMeteorologiaBBDD;
import Model.Peticiones.PeticionesServidor;

import java.util.List;

// Clase encargada de realizar las peticiones y convertir el resultado de las peticiones en datos del modelo.
// Utiliza la clase JSONParser para procesar los Strings recibidos.
public class GestorPeticiones implements InterfaceGestorPeticiones {

    private Controller.GestorBBDD GestorBBDD;
    private Model.Peticiones.PeticionesServidor PeticionesServidor;
    private Model.Peticiones.InsertarMeteorologiaBBDD InsertarMeteorologiaBBDD;
    private Controller.JSONParser JSONParser;

    public GestorPeticiones() {
        GestorBBDD = new GestorBBDD();
        PeticionesServidor = new PeticionesServidor();
        InsertarMeteorologiaBBDD = new InsertarMeteorologiaBBDD();
        JSONParser = new JSONParser();
    }

    public DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) throws LocationNotFoundException {
        String outApi = PeticionesServidor.peticionCiudadHoy(ciudad);
        DatosMeteorologia resultado = JSONParser.convertirDatosUnicos(outApi);
        resultado.setUbicacion(ciudad);
        InsertarMeteorologiaBBDD.actualCiudad(resultado);
        return resultado;
    }

    public DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) throws LocationNotFoundException {
        String outApi = PeticionesServidor.peticionCoordenadasHoy(coordenadas);
        DatosMeteorologia resultado = JSONParser.convertirDatosUnicos(outApi);
        resultado.setUbicacion(coordenadas);
        InsertarMeteorologiaBBDD.actualCoordenadas(resultado);
        return resultado;
    }

    public List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) throws LocationNotFoundException {
        if(dias > 4 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");
        String outApi = PeticionesServidor.peticionCiudad5Dias(ciudad);
        List<DatosMeteorologia> lista = JSONParser.convertirListaDatos(outApi);
        for(DatosMeteorologia dm : lista)
            dm.setUbicacion(ciudad);
        InsertarMeteorologiaBBDD.previsionCiudad(lista);
        lista = lista.subList(0, dias*8);
        return lista;
    }

    public List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) throws LocationNotFoundException {
        if(dias > 4 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");
        String outApi = PeticionesServidor.peticionCoordenadas5Dias(coordenadas);
        List<DatosMeteorologia> lista = JSONParser.convertirListaDatos(outApi);
        for(DatosMeteorologia dm : lista)
            dm.setUbicacion(coordenadas);
        InsertarMeteorologiaBBDD.previsionCoordenadas(lista);
        lista = lista.subList(0, dias*8);
        return lista;
    }

    public String obtenerEtiqueta(Coordenadas coordenadas) throws LocationNotFoundException {
        String outApi = PeticionesServidor.peticionCoordenadasHoy(coordenadas);
        String resultado = JSONParser.convertirEtiqueta(outApi);
        return resultado;
    }

}
