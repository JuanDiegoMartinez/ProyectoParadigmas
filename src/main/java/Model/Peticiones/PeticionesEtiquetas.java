package Model.Peticiones;

import Controller.BBDD;
import Model.Datos.Coordenadas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PeticionesEtiquetas {

    public static boolean modificarEtiqueta(String etiqueta, Coordenadas coordenadas) {

        boolean estaVieja = AuxFavoritos.comprobarEtiqueta(coordenadas.getEtiqueta());
        boolean estaNueva = AuxFavoritos.comprobarEtiqueta(etiqueta);

        if (estaVieja && ! estaNueva) {
            String sql = "UPDATE Favoritos SET Etiqueta = ? WHERE Latitud = ? AND Longitud = ?";

            try {
                Connection con = BBDD.getConn();
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, etiqueta);
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
}
