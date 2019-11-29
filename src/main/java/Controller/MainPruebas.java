package Controller;

import Model.Datos.Ciudad;
import Model.Peticiones.PeticionesServidor;

public class MainPruebas {

    public static void main(String[] args){
        Ciudad c = new Ciudad("Madrid");

        // Normalmente se usará como GestorPeticiones.obtenerTiempoHoyCiudad(c), esto es solo para comprobar la key
        // La api key tarda un tiempo en activarse, si no funciona la petición (se printea null) puede ser por eso
        System.out.println(PeticionesServidor.peticionCiudadHoy(c));
    }

}
