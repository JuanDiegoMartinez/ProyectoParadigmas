package Model.Peticiones;

import Controller.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuxPeticionesBBDD {

    public static boolean comprobarFechayHoraCiudad(String ciudad) {

        String sql = "SELECT Fecha, Hora FROM Actual WHERE Nombre = ?";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ciudad);
            ResultSet rs = stmt.executeQuery();

            return comprobarFechayHora(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean comprobarFechayHoraCoordenadas(double latitud, double longitud) {

        String sql = "SELECT Fecha, Hora FROM Actual WHERE Latitud = ? AND Longitud = ?";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            ResultSet rs = stmt.executeQuery();

            return comprobarFechayHora(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean comprobarFechaCiudad(String ciudad) {

        String sql = "SELECT MIN(Fecha) AS fecha FROM Prevision WHERE Nombre = ?";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, ciudad);
            ResultSet rs = stmt.executeQuery();

            return comprobarFecha(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean comprobarFechaCoordenadas(double latitud, double longitud) {

        String sql = "SELECT MIN(Fecha) AS fecha FROM Prevision WHERE Latitud = ? AND Longitud = ?";

        try {
            //Conexión a la base de datos y ejecución de query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, latitud);
            stmt.setDouble(2, longitud);
            ResultSet rs = stmt.executeQuery();

            return comprobarFecha(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean comprobarFechayHora(ResultSet rs) {

        try {
            //Fecha y hora actual
            Date f = new Date();
            DateFormat hourFormat = new SimpleDateFormat("HH");
            int horaActual = Integer.parseInt(hourFormat.format(f));
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
            String fechaActual = dateFormat.format(f);

            //Fecha y hora de la consulta
            String fecha = rs.getString("Fecha").substring(5,10);
            int hora = Integer.parseInt(rs.getString("Hora").substring(0,2));

            if (fecha.equals(fechaActual) && horaActual - hora <= 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean comprobarFecha(ResultSet rs) {

        try {
            //Fecha actual
            Date f = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM-dd");
            String fechaActual = dateFormat.format(f);

            //Fecha de la consulta
            String fecha = rs.getString("fecha").substring(5,10);

            if (fecha.equals(fechaActual)) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
