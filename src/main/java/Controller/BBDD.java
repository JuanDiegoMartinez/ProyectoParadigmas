package Controller;

import java.sql.*;

public class BBDD {

    private static Connection conn = null;

    //Obtener la conexión a la base de datos
    public static Connection getConn() {

        if (conn == null) {

            try {
                String url = "jdbc:sqlite:Paradigmas.db";
                conn = DriverManager.getConnection(url);
                System.out.println("Conectado con la base de datos");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        return conn;
    }

    public static void close() throws SQLException {
        conn.close();
    }

}
