import Controller.BBDD;
import Model.Datos.Coordenadas;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU10 {

    //Primer escenario
    @Test
    public void favoritos_darBajaCoordenadasCorrecto() {

        //Insertamos datos en la BBDD
        String sql = "INSERT INTO Favoritos VALUES('(15.234, -2.454)', null, 15.234, -2.454)";

        try {
            //Obtenemos la conexión de la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            //Creamos una coordenadas
            Coordenadas coordenadas = new Coordenadas(15.234, -2.454);

            //Método a probar
            Assert.assertEquals(true,  SistemaFacade.bajaCoordenadasFavoritos(coordenadas));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void favoritos_darBajaCoordenadasIncorrecto() {

        //Creamos unas coordenadas
        Coordenadas coordenadas = new Coordenadas(15.234, -2.454);

        //Método a probar
        Assert.assertEquals(false,  SistemaFacade.bajaCoordenadasFavoritos(coordenadas));
    }
}
