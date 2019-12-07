import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import Excepciones.LocationNotFoundExcepion;
import static org.junit.Assert.assertEquals;

public class HU01 {

    //Test del primer escenario
    @Test
    public void datosMeteorologicoshoy_ciudadCorrecta() {
        Ciudad c = new Ciudad("Madrid");
        DatosMeteorologia res = GestorPeticiones.obtenerTiempoHoyCiudad(c);
    }

    //Test del segundo escenario
    @Test(expected = LocationNotFoundException.class)
    public void datosMeteorologicoshoy_ciudadErronea() {
        Ciudad c = new Ciudad("Madrod");
        DatosMeteorologia res = GestorPeticiones.obtenerTiempoHoyCiudad(c);
    }
}
