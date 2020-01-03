import Controller.BBDD;
import Excepciones.LocationNotFoundException;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HU06 {

    //Primer escenario
    @Test
    public void datosMeteorologiaBBDD_coordenadasHoy() {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Actual VALUES(?,?,'Despejado', 2, 3, 4, 2, null, -4.324, 40.123)";

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

            //Creamos una nueva coordenada y llamamos al método a probar
            Coordenadas coordenadas = new Coordenadas(-4.324, 40.123);
            DatosMeteorologia tiempo = SistemaFacade.obtenerTiempoHoyCoordenadas(coordenadas);

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
            sql = "DELETE FROM Actual WHERE Latitud = -4.324 AND Longitud = 40.123";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void datosMeteorologiaBBDD_coordenadasSinConexion() {
        Coordenadas coordenadas = new Coordenadas(-4.324, 40.123);
        SistemaFacade.obtenerTiempoHoyCoordenadas(coordenadas);
    }

}
