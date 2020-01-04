package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PeticionesFavoritos {

    public static boolean anadirCiudad(Ciudad ciudad) {

        boolean esta = AuxFavoritos.comprobarCiudad(ciudad.getNombre());

        if (! esta) {

            String sql = "INSERT INTO Favoritos VALUES(?, ?, null, null)";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, ciudad.getNombre());
                stmt.setString(2, ciudad.getNombre());
                stmt.executeUpdate();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean borrarCiudad(Ciudad ciudad) {

        boolean esta = AuxFavoritos.comprobarCiudad(ciudad.getNombre());

        if (esta) {

            String sql = "DELETE FROM Favoritos WHERE Nombre = ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, ciudad.getNombre());
                stmt.executeUpdate();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean anadirCoordenadas(Coordenadas coordenadas) {

        boolean estaC = AuxFavoritos.comprobarCoordenadas(coordenadas.getLatitud(), coordenadas.getLongitud());

        if (! estaC) {

            String sql = "INSERT INTO Favoritos VALUES(?, null, ?, ?)";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, "");
                stmt.setDouble(2, coordenadas.getLatitud());
                stmt.setDouble(3, coordenadas.getLongitud());
                stmt.executeUpdate();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean borrarCoordenadas(Coordenadas coordenadas) {

        boolean esta = AuxFavoritos.comprobarCoordenadas(coordenadas.getLatitud(), coordenadas.getLongitud());

        if (esta) {

            String sql = "DELETE FROM Favoritos WHERE Latitud = ? AND Longitud = ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setDouble(1, coordenadas.getLatitud());
                stmt.setDouble(2, coordenadas.getLongitud());
                stmt.executeUpdate();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

