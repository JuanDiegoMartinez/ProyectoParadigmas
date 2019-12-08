import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import org.junit.Assert;
import org.junit.Test;

public class HU02 {

    // Primer escenario
    @Test
    public void datosMeteorologicoshoy_coordenadasCorrectas() throws LocationNotFoundException {
        Coordenadas coordenadas = new Coordenadas(39, -31);
        DatosMeteorologia tiempo = GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
        Assert.assertNotEquals(tiempo.getUbicacion(), null);
        Assert.assertNotEquals(tiempo.getDia(), null);
        Assert.assertNotEquals(tiempo.getHora(), null);
        Assert.assertNotEquals(tiempo.getTipoDia(), null);
    }

    // Segundo escenario
    @Test(expected = LocationNotFoundException.class)
    public void datosMeteorologicoshoy_coordenadasErroneas() throws LocationNotFoundException {
        Coordenadas coordenadas = new Coordenadas(121, -31);
        DatosMeteorologia tiempo = GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
    }
}
