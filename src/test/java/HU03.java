import static org.junit.Assert.assertEquals;

public class HU03 {

    // Primer escenario
    @Test
    public void datosMeteorologicosProx3Dias_ciudadCorrecta() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        SistemaFacade sistema = new SistemaFacade();
        assertEquals(datosMeteorologia1, sistema.obtenerTiempoXdiasCiudad("Madrid", 3));

    }

    // Segundo escenario
    @Test
    public void datosMeteorologicosProx3Dias_ciudadErronea() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("Madrid", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        SistemaFacade sistema = new SistemaFacade();
        assertEquals(datosMeteorologia1, sistema.obtenerTiempoXdiasCiudad("Madrod", 3));

    }

}