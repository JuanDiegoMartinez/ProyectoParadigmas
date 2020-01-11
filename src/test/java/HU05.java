import Controller.BBDD;
import Controller.GestorBBDD;
import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HU05 {

    //Primer escenario
    @Test
    public void datosMeteorologiaBBDD_ciudadHoy() {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Actual VALUES(?,?,'Despejado', 2, 3, 4, 2, 'Madrid', null, null)";

        try {
            //Obtenemos la conexión de la BBDD y preparamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);

            //Creamos la fecha y hora actuales
            Date f = new Date();
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = dateFormat.format(f);
            String hora = hourFormat.format(f);

            //Añadimos la hora y la fecha actual a la query y la ejecutamos
            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            stmt.executeUpdate();

            //Creamos una nueva ciudad y llamamos al método a probar
            Ciudad ciudad = new Ciudad("Madrid");
            DatosMeteorologia tiempo = new SistemaFacade().obtenerTiempoHoyCiudad(ciudad);

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

            //Borramos la query insertada anteriormente
            sql = "DELETE FROM Actual WHERE Nombre = 'Madrid'";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException | LocationNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void datosMeteorologiaBBDD_ciudadSinConexion() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrid");
        GestorBBDD g = Mockito.mock(GestorBBDD.class);
        GestorPeticiones p = Mockito.mock(GestorPeticiones.class);
        SistemaFacade s = new SistemaFacade(g, p);
        Mockito.when(g.obtenerTiempoHoyCiudad(ciudad)).thenReturn(null);
        Mockito.when(p.obtenerTiempoHoyCiudad(ciudad)).thenReturn(null);
        DatosMeteorologia resultado = s.obtenerTiempoHoyCiudad(ciudad);
        Assert.assertEquals(null, resultado);
    }

}
