import static org.junit.Assert.assertEquals;

public class HU04 {

    // Primer escenario
    @Test
    public void datosMeteorologicosProx3Dias_coordenadasCorrectas() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("(-4.324,40.343)", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        SistemaFacade sistema = new SistemaFacade();
        assertEquals(datosMeteorologia1, sistema.obtenerTiempoXdiasCoordenadas("(-4.324,40.343)", 3));

    }

    // Segundo escenario
    @Test
    public void datosMeteorologicosProx3Dias_coordenadasErroneas() {

        DatosMeteorologia datosMeteorologia1 = new DatosMeteorologia("(-4.324,40.343)", "03/11/19", "Soleado", "20 ºC", "0%", "15 m/s");
        SistemaFacade sistema = new SistemaFacade();
        assertEquals(datosMeteorologia1, sistema.obtenerTiempoXdiasCoordenadas("(181.323,95.314)", 3));

    }

}