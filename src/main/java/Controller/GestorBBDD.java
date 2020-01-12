package Controller;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.PeticionesBBDD;

import java.sql.ResultSet;
import java.util.List;

// Clase encargada de realizar las peticiones y convertir el resultado de las peticiones en datos del modelo.
// Utiliza la clase JSONParser para procesar los Strings recibidos.
public class GestorBBDD implements InterfaceGestorBBDD {

    private Controller.BBDDParser BBDDParser;
    private Model.Peticiones.PeticionesBBDD PeticionesBBDD;

    public GestorBBDD() {
        BBDDParser = new BBDDParser();
        PeticionesBBDD = new PeticionesBBDD();
    }

    public DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) {

        ResultSet rs = PeticionesBBDD.bbddCiudadHoy(ciudad);

        if (rs == null) {
            return null;
        }

        DatosMeteorologia resultado = BBDDParser.convertirDatosUnicos(rs);
        resultado.setUbicacion(ciudad);
        return resultado;
    }

    public DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) {

        ResultSet rs = PeticionesBBDD.bbddCoordenadasHoy(coordenadas);

        if (rs == null) {
            return null;
        }

        DatosMeteorologia resultado = BBDDParser.convertirDatosUnicos(rs);
        resultado.setUbicacion(coordenadas);
        return resultado;
    }

    public List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) {

        if(dias > 5 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");

        ResultSet rs = PeticionesBBDD.bbddCiudadXdias(ciudad, dias);

        if (rs == null) {
            return null;
        }

        List<DatosMeteorologia> resultado = BBDDParser.convertirListaDatos(rs);
        for(DatosMeteorologia dm : resultado)
            dm.setUbicacion(ciudad);

        return resultado;
    }

    public List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) {

        if(dias > 5 || dias <= 0)
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 4, y el mínimo 1.");

        ResultSet rs = PeticionesBBDD.bbddCoordenadasXdias(coordenadas, dias);

        if (rs == null) {
            return null;
        }

        List<DatosMeteorologia> resultado = BBDDParser.convertirListaDatos(rs);
        for(DatosMeteorologia dm : resultado)
            dm.setUbicacion(coordenadas);

        return resultado;
    }

}
