import Controller.BBDD;
import Model.Datos.Ubicacion;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HU11 {

    //Primer escenario
    @Test
    public void obtenerListaFavoritos() {

        //Lista de lugares favoritos
        String[] lista = {"Madrid", "Valencia", "Barcelona"};

        try {
            //Obtenemos la conexión a la base de datos
            Connection con = BBDD.getConn();

            //Insertamos los valores en la BBDD
            for(int i = 0; i < lista.length; i++) {
                String sql = "INSERT INTO Favoritos VALUES(?, ?, null, null)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.setString(2, lista[i]);
                stmt.executeUpdate();
            }

            //Obtenemos la lista de favoritos
            List<Ubicacion> favoritos = SistemaFacade.obtenerListaFavoritos();

            //Método a probar
            for(int i = 0; i < lista.length; i++) {
                Assert.assertEquals(lista[i], favoritos.get(i).getEtiqueta());
            }

            //Insertamos los valores en la BBDD
            for(int i = 0; i < lista.length; i++) {
                String sql = "DELETE FROM Favoritos WHERE Nombre = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, lista[i]);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
