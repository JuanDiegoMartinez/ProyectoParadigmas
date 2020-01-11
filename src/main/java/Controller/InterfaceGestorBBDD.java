package Controller;

import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;

import java.util.List;

public interface InterfaceGestorBBDD {

    public DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad);

    public DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas);

    public List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias);

    public List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias);
}
