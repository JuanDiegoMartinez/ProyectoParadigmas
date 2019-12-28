import Controller.GestorBBDD;
import Model.Datos.Ciudad;
import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.Test;
import org.mockito.Mockito;

public class HU05 {

    //Primer escenario
    @Test
    public void datosMeteorologiaBBDD_ciudadHoy() {
        Ciudad ciudad = new Ciudad("Madrid");
        GestorBBDD mock = Mockito.mock(GestorBBDD.class);
        SistemaFacade a = new SistemaFacade(mock);
        DatosMeteorologia datos = a.obtenerTiempoHoyCiudad(ciudad);
        Mockito.verify(mock).obtenerTiempoHoyCiudad(ciudad);
    }
}
