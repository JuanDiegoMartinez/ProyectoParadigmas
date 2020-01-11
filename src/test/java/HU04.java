import Excepciones.LocationNotFoundException;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HU04 {

    // Primer escenario
    @Test
    public void datosMeteorologicosProx3Dias_coordenadasCorrectas() throws LocationNotFoundException {
        Coordenadas coordenadas = new Coordenadas(25, -27);
        List<DatosMeteorologia> resultado = new SistemaFacade().obtenerTiempoXdiasCoordenadas(coordenadas, 3);
        Assert.assertEquals(24, resultado.size()); // 3 dias * 8 predicciones diarias de la api
        for(DatosMeteorologia tiempo : resultado) {
            // Comprobar que se han inyectado los valores necesarios
            Assert.assertNotEquals(tiempo.getUbicacion(), null);
            Assert.assertNotEquals(tiempo.getDia(), null);
            Assert.assertNotEquals(tiempo.getHora(), null);
            Assert.assertNotEquals(tiempo.getTipoDia(), null);

            // Comprobar que los valores numéricos inyectados son posibles/válidos
            Assert.assertFalse(tiempo.getTempMax() > 60 || tiempo.getTempMax() < -20);
            Assert.assertFalse(tiempo.getTempMin() > 60 || tiempo.getTempMin() < -20);
            Assert.assertFalse(tiempo.getHumedad() < 0 || tiempo.getHumedad() > 100);
            Assert.assertFalse(tiempo.getViento() < 0);
        }
    }

    // Segundo escenario
    @Test(expected = LocationNotFoundException.class)
    public void datosMeteorologicosProx3Dias_coordenadasErroneas() throws LocationNotFoundException {
        Coordenadas coordenadas = new Coordenadas(25, -197);
        List<DatosMeteorologia> resultado = new SistemaFacade().obtenerTiempoXdiasCoordenadas(coordenadas, 3);
    }

}