package Controller;

import Model.Datos.DatosMeteorologia;
import Model.Datos.Ubicacion;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

// Clase encargada de convertir los json como Strings en datos del modelo
public class JSONParser {

    public static List<DatosMeteorologia> convertirListaDatos(String json) {
        // TODO
        try {
            JSONObject o = new JSONObject(json);
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    public static DatosMeteorologia convertirDatosUnicos(String json) {
        try {
            // Parsear JSON y crear el objeto resultado
            JSONObject o = new JSONObject(json);
            DatosMeteorologia resultado = new DatosMeteorologia();

            // Obtener datos del json e inyectarlos al resultado
            resultado.setDia(LocalDate.now());
            resultado.setTipoDia(o.getJSONArray("weather").getJSONObject(0).getString("main"));
            resultado.setTemp(o.getJSONObject("main").getDouble("temp") - 273.15);
            resultado.setHumedad(o.getJSONObject("main").getDouble("humidity"));
            resultado.setViento(o.getJSONObject("wind").getDouble("speed"));

            return resultado;
        } catch (JSONException e) {
            return null;
        }
    }

}
