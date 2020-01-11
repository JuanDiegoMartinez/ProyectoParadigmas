import Controller.BBDD;
import Model.Datos.Coordenadas;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU13 {

    //Primer escenario
    @Test
    public void modificarEtiqueta() {

        //Datos para insertar en la BBDD
        String[] lista = {"Universidad", "Trabajo"};
        Coordenadas[] cor = {new Coordenadas(39.994, -0.068), new Coordenadas(39.882, -0.276)};
        cor[0].setEtiqueta("Universidad");
        cor[1].setEtiqueta("Trabajo");

        try {
            //Obtenemos la conexión a la BBDD
            Connection con = BBDD.getConn();

            //Insertamos los datos en la BBDD
            for(int i = 0; i < lista.length; i++) {
                String sql = "INSERT INTO Favoritos VALUES(?, null, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.setDouble(2, cor[i].getLatitud());
                stmt.setDouble(3, cor[i].getLongitud());
                stmt.executeUpdate();
            }

            //Método a probar
            String etiqueta = "UJI";
            Assert.assertEquals(true, new SistemaFacade().modificarEtiqueta(etiqueta, cor[0]));

            //Borramos los datos en la BBDD
            lista[0] = "UJI";

            for(int i = 0; i < lista.length; i++) {
                String sql = "DELETE FROM Favoritos WHERE Etiqueta = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void modificarEtiquetaIncorrecto() {

        //Datos para insertar en la BBDD
        String[] lista = {"Universidad", "Trabajo"};
        Coordenadas[] cor = {new Coordenadas(39.994, -0.068), new Coordenadas(39.882, -0.276)};
        cor[0].setEtiqueta("Universidad");
        cor[1].setEtiqueta("Trabajo");

        try {
            //Obtenemos la conexión a la BBDD
            Connection con = BBDD.getConn();

            //Insertamos los datos en la BBDD
            for(int i = 0; i < lista.length; i++) {
                String sql = "INSERT INTO Favoritos VALUES(?, null, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.setDouble(2, cor[i].getLatitud());
                stmt.setDouble(3, cor[i].getLongitud());
                stmt.executeUpdate();
            }

            //Método a probar
            String etiqueta = "Trabajo";
            Assert.assertEquals(false, new SistemaFacade().modificarEtiqueta(etiqueta, cor[0]));

            //Borramos los datos en la BBDD
            for(int i = 0; i < lista.length; i++) {
                String sql = "DELETE FROM Favoritos WHERE Etiqueta = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
