package Controller;

import Model.Datos.DatosMeteorologia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Clase para convertir las querys de la bbdd en DatosMeteorologia
public class BBDDParser {

    public static DatosMeteorologia convertirDatosUnicos(ResultSet rs) {

        DatosMeteorologia datos = null;

        try {
            LocalDate fecha = LocalDate.parse(rs.getString("Fecha"));
            LocalTime hora = LocalTime.parse(rs.getString("Hora"));
            String dia = rs.getString("Cielo");
            double humedad = rs.getDouble("Humedad");
            double viento = rs.getDouble("Viento");
            double maxima = rs.getDouble("Maxima");
            double minima = rs.getDouble("Minima");

            datos = new DatosMeteorologia(null, fecha, hora, dia, humedad, viento, maxima, minima);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public static List<DatosMeteorologia> convertirListaDatos(ResultSet rs) {

        List<DatosMeteorologia> datos = new ArrayList<DatosMeteorologia>();

        try {

            while (rs.next()) {
                LocalDate fecha = LocalDate.parse(rs.getString("Fecha"));
                LocalTime hora = LocalTime.parse(rs.getString("Hora"));
                String dia = rs.getString("Cielo");
                double humedad = rs.getDouble("Humedad");
                double viento = rs.getDouble("Viento");
                double maxima = rs.getDouble("Maxima");
                double minima = rs.getDouble("Minima");

                DatosMeteorologia d = new DatosMeteorologia(null, fecha, hora, dia, humedad, viento, maxima, minima);
                datos.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }
}
