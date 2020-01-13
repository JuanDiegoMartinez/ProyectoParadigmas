import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.Datos.Ubicacion;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HU16 {

    //Primer escenario
    @Test
    public void ordenarAlfaAsc() {
        //Datos
        List<String> etiquetas = new ArrayList<>();
        etiquetas.add("madrid");
        etiquetas.add("valencia");
        etiquetas.add("barcelona");

        try {
            //Obtenemos la conexión a la BBDD
            Connection con = BBDD.getConn();

            //Insertamos los datos en la BBDD
            for(int i = 0; i < etiquetas.size(); i++) {
                String sql = "INSERT INTO Favoritos VALUES(?, ?, null, null)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, etiquetas.get(i));
                stmt.setString(2, etiquetas.get(i));
                stmt.executeUpdate();
            }
            //Método a probar
            Collections.sort(etiquetas, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
            
            new SistemaFacade().ordenar(etiquetas);
            String sql = "SELECT Etiqueta FROM Favoritos";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            String[] listaDevolver = {"valencia", "madrid", "barcelona"};
            int j = 0;

            while(rs.next()) {
                Assert.assertEquals(listaDevolver[j], rs.getString("Etiqueta"));
                j++;
            }

            //Borramos los datos en la BBDD
            for(int i = 0; i < etiquetas.size(); i++) {
                String sql1 = "DELETE FROM Favoritos WHERE Etiqueta = ?";
                PreparedStatement stmt1 = con.prepareStatement(sql1);
                stmt1.setString(1, etiquetas.get(i));
                stmt1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
