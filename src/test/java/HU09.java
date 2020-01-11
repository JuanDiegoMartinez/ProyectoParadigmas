import Controller.BBDD;
import Model.Datos.Coordenadas;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU09 {

    //Primer escenario
    @Test
    public void favoritos_darAltaCoordenadasCorrecto() {

        //Creamos unas nuevas coordenadas
        Coordenadas coordenadas = new Coordenadas(15.234, -2.454);

        //Método a probar
        Assert.assertEquals(true,  new SistemaFacade().altaCoordenadasFavoritos("", coordenadas));

        //Borramos la coordenada insertada
        String sql = "DELETE FROM Favoritos WHERE Latitud = 15.234 AND Longitud = -2.454";

        try {
            //Conexión a la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void favoritos_darAltaCoordenadasIncorrecto() {

        //Insertamos en la BBDD
        String sql = "INSERT INTO Favoritos VALUES('(15.234, -2.454)', null, 15.234, -2.454)";

        try {
            //Obtenemos la conexión de la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            //Creamos unas coordendas
            Coordenadas coordenadas = new Coordenadas(15.234, -2.454);

            //Método a probar
            Assert.assertEquals(false,  new SistemaFacade().altaCoordenadasFavoritos("", coordenadas));

            //Borramos los datos insertados en la BBDD
            sql = "DELETE FROM Favoritos WHERE Latitud = 15.234 AND Longitud = -2.454";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}