package Controller;

import Model.Datos.DatosMeteorologia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Clase encargada de convertir los json como Strings en datos del modelo
public class JSONParser {

    public List<DatosMeteorologia> convertirListaDatos(String json) {
        List<DatosMeteorologia> listaDatos = new ArrayList<>(5);

        //Obtener fecha de hoy
        Date f = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = dateFormat.format(f);

        //Obtener fecha dentro de 5 dias
        Calendar c = Calendar.getInstance();
        c.setTime(f);
        c.add(Calendar.DAY_OF_YEAR, 5);
        Date a = c.getTime();
        String fecha5dias = dateFormat.format(a);

        try {
            JSONObject o = new JSONObject(json);
            JSONArray listaTiempos = o.getJSONArray("list");
            for(int i = 0; i < listaTiempos.length(); i++) {
                JSONObject actual = listaTiempos.getJSONObject(i);
                DatosMeteorologia dm = new DatosMeteorologia();
                String[] fechaHora = actual.getString("dt_txt").split(" ");

                // Obtener e inyectar día y hora
                String[] fecha = fechaHora[0].split("-");
                String[] hora = fechaHora[1].split(":");

                if(! fechaHora[0].equals(fechaHoy) && ! fechaHora[0].equals(fecha5dias)) {
                    // inyectar día
                    dm.setDia(LocalDate.of(Integer.parseInt(fecha[0]),
                            Integer.parseInt(fecha[1]),
                            Integer.parseInt(fecha[2])));

                    // inyectar hora
                    dm.setHora(LocalTime.of(Integer.parseInt(hora[0]),
                            Integer.parseInt(hora[1]),
                            Integer.parseInt(hora[2])));

                    // Inyectar resto de datos
                    transferirDatos(actual, dm);

                    // Añadir a la lista
                    listaDatos.add(dm);
                }
            }
            return listaDatos;
        } catch (JSONException e) {
            return null;
        }
    }

    public DatosMeteorologia convertirDatosUnicos(String json) {
        try {
            // Parsear JSON y crear el objeto resultado
            JSONObject o = new JSONObject(json);
            DatosMeteorologia resultado = new DatosMeteorologia();

            // Inyectar dia y hora actuales (ya que la petición de tiempo actual
            // de la API no contiene un campo con el timestamp)
            resultado.setDia(LocalDate.now());
            resultado.setHora(LocalTime.now());

            // Inyectar resto de datos
            transferirDatos(o, resultado);

            return resultado;
        } catch (JSONException e) {
            return null;
        }
    }

    // Obteniene los datos que se puedan obtener de forma directa del JSONObject
    // y los inyecta en el objeto DatosMeteorologia ofrecido
    private void transferirDatos(JSONObject actual, DatosMeteorologia dm) {
        dm.setTipoDia(actual.getJSONArray("weather").getJSONObject(0).getString("main"));
        dm.setTempMin(actual.getJSONObject("main").getDouble("temp_min"));
        dm.setTempMax(actual.getJSONObject("main").getDouble("temp_max"));
        dm.setHumedad(actual.getJSONObject("main").getDouble("humidity"));
        dm.setViento(actual.getJSONObject("wind").getDouble("speed"));
    }

    public String convertirEtiqueta(String json) {
        JSONObject o = new JSONObject(json);
        String eti = o.getString("name");
        return eti;
    }

}
