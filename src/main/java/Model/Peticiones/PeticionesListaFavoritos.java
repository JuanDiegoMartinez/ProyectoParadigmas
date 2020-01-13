package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.Ubicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeticionesListaFavoritos {

    public  List<Ubicacion> obtenerFavoritos() {

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

    public void ordenar(List<String> ubicaciones) {

        String alter = "ALTER TABLE Favoritos RENAME TO Favoritos_old;";

        String tabla = "CREATE TABLE Favoritos ("
                + "    Etiqueta VARCHAR NOT NULL,\n"
                + "    Nombre VARCHAR NULL,\n"
                + "    Latitud DOUBLE NULL,\n"
                + "    Longitud DOUBLE NULL\n"
                + ");";

        try {
            Connection con = BBDD.getConn();
            Statement stmt = con.createStatement();

            stmt.execute(alter);

            stmt.execute(tabla);

            String sql = "INSERT INTO Favoritos(Etiqueta, Nombre, Latitud, Longitud) " +
                            "SELECT Etiqueta, Nombre, Latitud, Longitud " +
                            "FROM Favoritos_old WHERE LOWER(Etiqueta) = LOWER(?);";

            for (int i = 0; i < ubicaciones.size(); i++) {
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, ubicaciones.get(i));
                st.executeUpdate();
            }

            String drop = "DROP TABLE Favoritos_old;";
            stmt.execute(drop);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
