import Controller.BBDD;
import Excepciones.LocationNotFoundException;
import Model.Datos.Coordenadas;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HU14 {

    //Primer escenario
    @Test
    public void etiquetaAutomatica() throws LocationNotFoundException {

        //Obtenemos la etiqueta generada por geocodificación
        Coordenadas coordenadas = new Coordenadas(-3.702, 40.146);
        String a = SistemaFacade.obtenerEtiqueta(coordenadas);

        //Método a probar
        Assert.assertEquals(true,  SistemaFacade.altaCoordenadasFavoritos(a, coordenadas));

        //Borramos la coordenada insertada
        String sql = "DELETE FROM Favoritos WHERE Latitud = -3.702 AND Longitud = 40.146";

        try {
            //Conexión a la BBDD y ejecutamos la query
            Connection con = BBDD.getConn();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Segundo escenario
    @Test
    public void etiquetaAutomaticaError() {
        //Hacer un mock
    }
}
