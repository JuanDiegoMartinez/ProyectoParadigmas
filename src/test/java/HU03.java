import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.DatosMeteorologia;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HU03 {

    // Primer escenario
    @Test
    public void datosMeteorologicosProx3Dias_ciudadCorrecta() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrid");
        List<DatosMeteorologia> resultado = GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, 3);
        Assert.assertEquals(24, resultado.size()); // 3 dias * 8 predicciones diarias de la api
        for(DatosMeteorologia tiempo : resultado) {
            Assert.assertNotEquals(tiempo.getUbicacion(), null);
            Assert.assertNotEquals(tiempo.getDia(), null);
            Assert.assertNotEquals(tiempo.getHora(), null);
            Assert.assertNotEquals(tiempo.getTipoDia(), null);
        }
    }

    // Segundo escenario
    @Test(expected = LocationNotFoundException.class)
    public void datosMeteorologicosProx3Dias_ciudadErronea() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrod");
        List<DatosMeteorologia> resultado = GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, 3);
    }

}