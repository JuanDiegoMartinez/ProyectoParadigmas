import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU07 {

    //Primer escenario
    @Test
    public void favoritos_darAltaCiudadCorrecto() {

        //Creamos una nueva ciudad
        Ciudad ciudad = new Ciudad("Valencia");

        //Método a probar
        Assert.assertEquals(true,  SistemaFacade.altaCiudadFavoritos(ciudad));

        //Borramos la ciudad insertada
        String sql = "DELETE FROM Favoritos WHERE Nombre = 'Valencia'";

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
    public void favoritos_darAltaCiudadIncorrecto() {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Favoritos VALUES('Madrid', 'Madrid', null, null)";

        try {
            //Conexión a la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

            //Creamos la ciudad
            Ciudad ciudad = new Ciudad("Madrid");

            //Método a probar
            Assert.assertEquals(false,  SistemaFacade.altaCiudadFavoritos(ciudad));

            //Borramos el insert anterior
            sql = "DELETE FROM Favoritos WHERE Nombre = 'Madrid'";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
