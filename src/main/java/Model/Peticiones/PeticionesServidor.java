package Model.Peticiones;

import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

// Clase para comunicarse directamente con el servidor.
// Realiza la petición y devuelve el resultado como String sin procesar.
public class PeticionesServidor {

    private static String baseUrl = "http://api.openweathermap.org/data/2.5/";
    private static String apiKey = "1b335fa2b3e70bd72f7e3211de8d7ed8";

    // Métodos estáticos que crean la url correspondiente según la petición
    public String peticionCiudadHoy(Ciudad ciudad) throws LocationNotFoundException {
        String texto = baseUrl + "weather?q=" + ciudad.getNombre();
        texto += "&units=metric&appid=" + apiKey;
        return obtenerTexto(texto);
    }

    public String peticionCoordenadasHoy(Coordenadas coordenadas) throws LocationNotFoundException {
        String texto = baseUrl + "weather?lat=" + coordenadas.getLatitud() + "&lon=" + coordenadas.getLongitud();
        texto += "&units=metric&appid=" + apiKey;
        return obtenerTexto(texto);
    }

    public String peticionCiudad5Dias(Ciudad ciudad) throws LocationNotFoundException {
        String texto = baseUrl + "forecast?q=" + ciudad.getNombre();
        texto += "&units=metric&appid=" + apiKey;
        return obtenerTexto(texto);
    }

    public String peticionCoordenadas5Dias(Coordenadas coordenadas) throws LocationNotFoundException {
        String texto = baseUrl + "forecast?lat=" + coordenadas.getLatitud() + "&lon=" + coordenadas.getLongitud();
        texto += "&units=metric&appid=" + apiKey;
        return obtenerTexto(texto);
    }

    // Método para realizar la conexión al servidor
    private String obtenerTexto(String url) throws LocationNotFoundException {
        BufferedReader in = null;
        try {
            // Conectar
            URL sitioWeb = new URL(url);
            URLConnection conexion = sitioWeb.openConnection();
            in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

            // Construir la respuesta (String con formato JSON)
            StringBuilder respuesta = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                respuesta.append(inputLine);

            in.close();
            return respuesta.toString();
        } catch (IOException e) {
            throw new LocationNotFoundException("La ubicación utilizada no existe.");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
    }

}