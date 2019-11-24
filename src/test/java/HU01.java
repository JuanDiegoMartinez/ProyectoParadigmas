import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class HU01 {

    //private static List<DatosMeteorologia> datosMeteorologias;

    /*
    @BeforeClass
    public static void creaTiempos() {
        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        DatosMeteorologia datosMeteorologia2 = new DatosMeteorologia("Barcelona", "03/11/19", "Nublado", "15 ºC", "35%", "40 m/s");
        DatosMeteorologia datosMeteorologia3 = new DatosMeteorologia("Valencia", "03/11/19", "Soleado", "25 ºC", "0%", "5 m/s");

        datosMeteorologias = new ArrayList<DatosMeteorologia>();
        datosMeteorologias.add(datosMeteorologia1);
        datosMeteorologias.add(datosMeteorologia2);
        datosMeteorologias.add(datosMeteorologia3);
    }
     */

    //Test del primer escenario
    @Test
    public void datosMeteorologicoshoy_ciudadCorrecta() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");

        SistemaFacade sistema = new SistemaFacade();

        assertEquals(datosMeteorologia1, sistema.obtenerTiempoHoyCiudad("Madrid"));

    }

    //Test del segundo escenario
    @Test
    public void datosMeteorologicoshoy_ciudadErronea() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");

        SistemaFacade sistema = new SistemaFacade();

        assertEquals(datosMeteorologia1, sistema.obtenerTiempoHoyCiudad("Madrod"));
    }
}
