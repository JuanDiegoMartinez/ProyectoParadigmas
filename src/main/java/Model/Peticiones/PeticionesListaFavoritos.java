package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.Ubicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeticionesListaFavoritos {

    public static List<Ubicacion> obtenerFavoritos() {

        List<Ubicacion> favoritos = new ArrayList<Ubicacion>();
        String sql = "SELECT * FROM Favoritos";

        try {
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getString("Nombre") == null) {
                    Ubicacion ubi = new Coordenadas(rs.getDouble("Latitud"), rs.getDouble("Longitud"));
                    ubi.setEtiqueta(rs.getString("Etiqueta"));
                    favoritos.add(ubi);
                }
                else {
                    Ubicacion ubi = new Ciudad(rs.getString("Nombre"));
                    ubi.setEtiqueta(rs.getString("Etiqueta"));
                    favoritos.add(ubi);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoritos;
    }
}
