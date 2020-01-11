package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.DatosMeteorologia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InsertarMeteorologiaBBDD {

    private Model.Peticiones.EliminarMeteorologiaBBDD EliminarMeteorologiaBBDD;

    public InsertarMeteorologiaBBDD() {
        EliminarMeteorologiaBBDD = new EliminarMeteorologiaBBDD();
    }

    public void actualCiudad(DatosMeteorologia tiempo) {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Actual VALUES(?, ?, ?, ?, ?, ?, ?, ?, null, null)";

        EliminarMeteorologiaBBDD.eliminarActualCiudad(tiempo.getUbicacion().getNombre());

        try {
            //Obtenemos la conexión de la BBDD y preparamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);

            //Creamos la fecha y hora actuales
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = tiempo.getDia().format(formatter);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String hora = tiempo.getHora().format(formatter1);

            //Añadimos la hora y la fecha actual a la query y la ejecutamos
            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            stmt.setString(3, tiempo.getTipoDia());
            stmt.setDouble(4, tiempo.getHumedad());
            stmt.setDouble(5, tiempo.getViento());
            stmt.setDouble(6, tiempo.getTempMax());
            stmt.setDouble(7, tiempo.getTempMin());
            stmt.setString(8, tiempo.getUbicacion().getNombre());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualCoordenadas(DatosMeteorologia tiempo) {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Actual VALUES(?, ?, ?, ?, ?, ?, ?, null, ?, ?)";

        EliminarMeteorologiaBBDD.eliminarActualCoordenadas(tiempo.getUbicacion().getLatitud(), tiempo.getUbicacion().getLongitud());

        try {
            //Obtenemos la conexión de la BBDD y preparamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);

            //Creamos la fecha y hora actuales
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = tiempo.getDia().format(formatter);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String hora = tiempo.getHora().format(formatter1);

            //Añadimos la hora y la fecha actual a la query y la ejecutamos
            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            stmt.setString(3, tiempo.getTipoDia());
            stmt.setDouble(4, tiempo.getHumedad());
            stmt.setDouble(5, tiempo.getViento());
            stmt.setDouble(6, tiempo.getTempMax());
            stmt.setDouble(7, tiempo.getTempMin());
            stmt.setDouble(8, tiempo.getUbicacion().getLatitud());
            stmt.setDouble(9, tiempo.getUbicacion().getLongitud());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void previsionCiudad(List<DatosMeteorologia> lista) {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Prevision VALUES(?, ?, ?, ?, ?, ?, ?, ?, null, null)";

        EliminarMeteorologiaBBDD.eliminarPrevisionCiudad(lista.get(0).getUbicacion().getNombre());

        try {
            //Obtenemos la conexión de la BBDD y preparamos la query
            Connection con = BBDD.getConn();

            for (int i = 0; i < lista.size(); i++) {
                PreparedStatement stmt = con.prepareStatement(sql);

                //Creamos la fecha y hora actuales
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fecha = lista.get(i).getDia().format(formatter);
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
                String hora = lista.get(i).getHora().format(formatter1);

                //Añadimos la hora y la fecha actual a la query y la ejecutamos
                stmt.setString(1, fecha);
                stmt.setString(2, hora);
                stmt.setString(3, lista.get(i).getTipoDia());
                stmt.setDouble(4, lista.get(i).getHumedad());
                stmt.setDouble(5, lista.get(i).getViento());
                stmt.setDouble(6, lista.get(i).getTempMax());
                stmt.setDouble(7, lista.get(i).getTempMin());
                stmt.setString(8, lista.get(i).getUbicacion().getNombre());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void previsionCoordenadas(List<DatosMeteorologia> lista) {

        //Creamos la sentencia sql de insertar
        String sql = "INSERT INTO Prevision VALUES(?, ?, ?, ?, ?, ?, ?, null, ?, ?)";

        EliminarMeteorologiaBBDD.eliminarPrevisionCoordenadas(lista.get(0).getUbicacion().getLatitud(), lista.get(0).getUbicacion().getLongitud());

        try {
            //Obtenemos la conexión de la BBDD y preparamos la query
            Connection con = BBDD.getConn();

            for (int i = 0; i < lista.size(); i++) {
                PreparedStatement stmt = con.prepareStatement(sql);

                //Creamos la fecha y hora actuales
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String fecha = lista.get(i).getDia().format(formatter);
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
                String hora = lista.get(i).getHora().format(formatter1);

                //Añadimos la hora y la fecha actual a la query y la ejecutamos
                stmt.setString(1, fecha);
                stmt.setString(2, hora);
                stmt.setString(3, lista.get(i).getTipoDia());
                stmt.setDouble(4, lista.get(i).getHumedad());
                stmt.setDouble(5, lista.get(i).getViento());
                stmt.setDouble(6, lista.get(i).getTempMax());
                stmt.setDouble(7, lista.get(i).getTempMin());
                stmt.setDouble(8, lista.get(i).getUbicacion().getLatitud());
                stmt.setDouble(9, lista.get(i).getUbicacion().getLongitud());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
