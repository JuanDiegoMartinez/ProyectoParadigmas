import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.DatosMeteorologia;
import org.junit.Assert;
import org.junit.Test;


public class HU01 {

    // Primer escenario
    @Test
    public void datosMeteorologicoshoy_ciudadCorrecta() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrid");
        DatosMeteorologia tiempo = GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
        Assert.assertNotEquals(tiempo.getUbicacion(), null);
        Assert.assertNotEquals(tiempo.getDia(), null);
        Assert.assertNotEquals(tiempo.getTipoDia(), null);
    }

    // Segundo escenario
    @Test(expected = LocationNotFoundException.class)
    public void datosMeteorologicoshoy_ciudadErronea() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrod");
        DatosMeteorologia tiempo = GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
    }
}
