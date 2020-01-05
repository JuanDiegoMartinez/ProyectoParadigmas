import Controller.BBDD;
import Model.Datos.Coordenadas;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU12 {

    //Primer escenario
    @Test
    public void registrarEtiqueta() {

        try {
            //Obtenemos la conexión de la BBDD
            Connection con = BBDD.getConn();

            //Creamos una nueva etiqueta y coordenada
            String etiqueta = "Casa";
            Coordenadas c = new Coordenadas(42.504, 1.519);

            //Método a probar
            Assert.assertEquals(true, SistemaFacade.altaCoordenadasFavoritos(etiqueta, c));

            //Borrar los datos insertados
            String sql = "DELETE FROM Favoritos WHERE Etiqueta = 'Casa'";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void registrarEtiquetaIncorrecto() {

        //Coordenada con etiqueta para insertala en la BBDD
        String etiqueta = "Trabajo";
        Coordenadas cor = new Coordenadas(39.994, -0.068);
        String sql = "INSERT INTO Favoritos VALUES(?, null, ?, ?)";

        try {
            //Obtenemos la conexión a la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, etiqueta);
            stmt.setDouble(2, cor.getLatitud());
            stmt.setDouble(3, cor.getLongitud());
            stmt.executeUpdate();

            //Creamos una nueva coordenada y etiqueta
            etiqueta = "Trabajo";
            Coordenadas c = new Coordenadas(42.504, 1.519);

            //Método a probar
            Assert.assertEquals(false, SistemaFacade.altaCoordenadasFavoritos(etiqueta, c));

            //Borrar los datos insertados
            sql = "DELETE FROM Favoritos WHERE Etiqueta = 'Trabajo'";
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
