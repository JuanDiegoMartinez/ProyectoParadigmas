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

public class HU15 {

    //Primer escenario
    @Test
    public void ordenarAlfaDesc() {
        //Datos
        List<Ubicacion> lista = new ArrayList<Ubicacion>();
        String[] etiquetas = {"Madrid", "Valencia", "Barcelona"};
        Ciudad[] ciudades = {new Ciudad("Madrid"), new Ciudad("Valencia"), new Ciudad("Barcelona")};
        for (int i = 0; i < etiquetas.length; i++) {
            Ubicacion ubi = ciudades[i];
            ubi.setEtiqueta(etiquetas[i]);
            lista.add(ubi);
        }

        try {
            //Obtenemos la conexión a la BBDD
            Connection con = BBDD.getConn();

            //Insertamos los datos en la BBDD
            for(int i = 0; i < etiquetas.length; i++) {
                String sql = "INSERT INTO Favoritos VALUES(?, ?, null, null)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, etiquetas[i]);
                stmt.setString(2, etiquetas[i]);
                stmt.executeUpdate();
            }
            //Método a probar
            Collections.sort(lista, new Comparator<Ubicacion>() {
                @Override
                public int compare(Ubicacion o1, Ubicacion o2) {
                    return o1.getEtiqueta().compareTo(o2.getEtiqueta());
                }
            });

            new SistemaFacade().ordenar(lista);
            String sql = "SELECT Etiqueta FROM Favoritos";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            String[] listaDevolver = {"Barcelona", "Madrid", "Valencia"};
            int j = 0;

            while(rs.next()) {
                Assert.assertEquals(listaDevolver[j], rs.getString("Etiqueta"));
                j++;
            }

            //Borramos los datos en la BBDD
            for(int i = 0; i < lista.size(); i++) {
                String sql1 = "DELETE FROM Favoritos WHERE Etiqueta = ?";
                PreparedStatement stmt1 = con.prepareStatement(sql1);
                stmt1.setString(1, etiquetas[i]);
                stmt1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
