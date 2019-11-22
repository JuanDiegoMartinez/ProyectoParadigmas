import Model.Tiempo;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class HU01 {

    private static List<Tiempo> tiempos;

    @BeforeClass
    public static void creaTiempos() {
        Tiempo tiempo1 = new Tiempo("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        Tiempo tiempo2 = new Tiempo("Barcelona", "03/11/19", "Nublado", "15 ºC", "35%", "40 m/s");
        Tiempo tiempo3 = new Tiempo("Valencia", "03/11/19", "Soleado", "25 ºC", "0%", "5 m/s");

        tiempos = new ArrayList<Tiempo>();
        tiempos.add(tiempo1);
        tiempos.add(tiempo2);
        tiempos.add(tiempo3);
    }

    //Test del primer escenario
    @Test
    public void datosMeteorologicoshoy_ciudadCorrecta() {

        String ciudad = "Madrid";
        boolean esta = false;

        for (Tiempo t : tiempos) {

            if (t.getUbicacion().toLowerCase().equals(ciudad.toLowerCase())) {
                esta = true;
            }
        }

        assertEquals(true, esta);
    }

    //Test del segundo escenario
    @Test
    public void datosMeteorologicoshoy_ciudadErronea() {

        String ciudad = "Madrod";
        boolean esta = false;

        for (Tiempo t : tiempos) {

            if (t.getUbicacion().toLowerCase().equals(ciudad.toLowerCase())) {
                esta = true;
            }
        }

        assertEquals(false, esta);
    }
}
