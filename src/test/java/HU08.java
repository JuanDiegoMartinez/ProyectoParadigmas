import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU08 {

    //Primer escenario
    @Test
    public void favoritos_darBajaCiudadCorrecto() {

        //Insertar una ciudad en la BBDD
        String sql = "INSERT INTO Favoritos VALUES('Valencia', 'Valencia', null, null)";

        try {
            //Obtenemos la conexión y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            //Creamos una ciudad
            Ciudad ciudad = new Ciudad("Valencia");

            //Método a probar
            Assert.assertEquals(true,  SistemaFacade.bajaCiudadFavoritos(ciudad));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void favoritos_darBajaCiudadIncorrecto() {

        //Creamos una nueva ciudad
        Ciudad ciudad = new Ciudad("Estocolmo");

        //Método a probar
        Assert.assertEquals(false,  SistemaFacade.bajaCiudadFavoritos(ciudad));
    }
}