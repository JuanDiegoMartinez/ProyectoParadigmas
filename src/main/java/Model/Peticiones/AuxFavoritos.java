package Model.Peticiones;

import Controller.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuxFavoritos {

    public boolean comprobarCiudad(String ciudad) {

        String sql = "SELECT Nombre FROM Favoritos WHERE LOWER(Nombre) = LOWER(?)";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ciudad);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean comprobarCoordenadas(double latitud, double longitud) {

        String sql = "SELECT Latitud, Longitud FROM Favoritos WHERE Latitud = ? AND Longitud = ?";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean comprobarEtiqueta(String etiqueta) {

        String sql = "SELECT Etiqueta FROM Favoritos WHERE LOWER(Etiqueta) = LOWER(?)";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, etiqueta);
            ResultSet rs = stmt.executeQuery();


            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
