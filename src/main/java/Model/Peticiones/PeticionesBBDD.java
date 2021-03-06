package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Clase para comunicarse directamente con la base de datos
// Realiza la petición y devuelve el resultado como String sin procesar.
public class PeticionesBBDD {

    private InterfaceAuxPeticionesBBDD AuxPeticionesBBDD;

    public PeticionesBBDD(InterfaceAuxPeticionesBBDD a) {
        AuxPeticionesBBDD = a;
    }

    public PeticionesBBDD() {
        AuxPeticionesBBDD = new AuxPeticionesBBDD();
    }

    //Obtener los datos consultados de una ciudad
    public ResultSet bbddCiudadHoy(Ciudad ciudad) {

        //Comprobar que la hora y la fecha son válidas
        boolean consultaValida = AuxPeticionesBBDD.comprobarFechayHoraCiudad(ciudad.getNombre());

        if (consultaValida) {

            String sql = "SELECT Fecha, Hora, Cielo, Humedad, Viento, Maxima, Minima FROM Actual WHERE LOWER(Nombre) = LOWER(?)";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, ciudad.getNombre());
                ResultSet rs = stmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ResultSet bbddCoordenadasHoy(Coordenadas coordenadas) {

        //Comprobar que la hora y la fecha son válidas
        boolean consultaValida = AuxPeticionesBBDD.comprobarFechayHoraCoordenadas(coordenadas.getLatitud(), coordenadas.getLongitud());

        if (consultaValida) {

            String sql = "SELECT Fecha, Hora, Cielo, Humedad, Viento, Maxima, Minima FROM Actual WHERE Latitud = ? AND Longitud = ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setDouble(1, coordenadas.getLatitud());
                stmt.setDouble(2, coordenadas.getLongitud());
                ResultSet rs = stmt.executeQuery();
                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ResultSet bbddCiudadXdias(Ciudad ciudad, int dias) {

        //Comprobar que la fecha es válida
        boolean consultaValida = AuxPeticionesBBDD.comprobarFechaCiudad(ciudad.getNombre());

        if (consultaValida) {

            String sql = "SELECT Fecha, Hora, Cielo, Humedad, Viento, Maxima, Minima FROM Prevision WHERE LOWER(Nombre) = LOWER(?) AND Fecha BETWEEN ? AND ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, ciudad.getNombre());

                Date f = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Calendar c = Calendar.getInstance();
                c.setTime(f);
                c.add(Calendar.DAY_OF_YEAR, 1);
                Date a = c.getTime();
                String fecha = dateFormat.format(a);
                stmt.setString(2, fecha);

                c = Calendar.getInstance();
                c.setTime(f);
                c.add(Calendar.DAY_OF_YEAR, dias);
                a = c.getTime();
                fecha = dateFormat.format(a);
                stmt.setString(3, fecha);

                ResultSet rs = stmt.executeQuery();

                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ResultSet bbddCoordenadasXdias(Coordenadas coordenadas, int dias) {

        //Comprobar que la fecha es válida
        boolean consultaValida = AuxPeticionesBBDD.comprobarFechaCoordenadas(coordenadas.getLatitud(), coordenadas.getLongitud());

        if (consultaValida) {

            String sql = "SELECT Fecha, Hora, Cielo, Humedad, Viento, Maxima, Minima FROM Prevision WHERE Latitud = ? AND Longitud = ? AND Fecha BETWEEN ? AND ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setDouble(1, coordenadas.getLatitud());
                stmt.setDouble(2, coordenadas.getLongitud());

                Date f = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Calendar c = Calendar.getInstance();
                c.setTime(f);
                c.add(Calendar.DAY_OF_YEAR, 1);
                Date a = c.getTime();
                String fecha = dateFormat.format(a);
                stmt.setString(3, fecha);

                c = Calendar.getInstance();
                c.setTime(f);
                c.add(Calendar.DAY_OF_YEAR, dias);
                a = c.getTime();
                fecha = dateFormat.format(a);
                stmt.setString(4, fecha);

                ResultSet rs = stmt.executeQuery();
                return rs;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
