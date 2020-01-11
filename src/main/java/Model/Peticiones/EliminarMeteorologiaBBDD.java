package Model.Peticiones;

import Controller.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarMeteorologiaBBDD {

    public void eliminarActualCiudad(String ciudad) {

        String sql = "DELETE FROM Actual WHERE LOWER(Nombre) = LOWER(?)";

        try {
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ciudad);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarActualCoordenadas(double latitud, double longitud) {

        String sql = "DELETE FROM Actual WHERE Latitud = ? AND Longitud = ?";

        try {
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrevisionCiudad(String ciudad) {

        String sql = "DELETE FROM Prevision WHERE LOWER(Nombre) = LOWER(?)";

        try {
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ciudad);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPrevisionCoordenadas(double latitud, double longitud) {

        String sql = "DELETE FROM Prevision WHERE Latitud = ? AND Longitud = ?";

        try {
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
