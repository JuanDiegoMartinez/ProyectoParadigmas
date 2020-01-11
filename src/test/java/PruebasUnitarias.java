import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.InsertarMeteorologiaBBDD;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PruebasUnitarias {

    @Test
    public void tiempoActualCiudad() throws LocationNotFoundException {

        Ciudad ubi = new Ciudad("Madrid");
        DatosMeteorologia tiempo = new DatosMeteorologia(ubi, LocalDate.now(), LocalTime.now().withNano(0), "Despejado", 1, 2, 3, 4);
        new InsertarMeteorologiaBBDD().actualCiudad(tiempo);
        DatosMeteorologia tiempoRes = new SistemaFacade().obtenerTiempoHoyCiudad(ubi);

        Assert.assertEquals(true, tiempo.getTempMin() == tiempoRes.getTempMin());
        Assert.assertEquals(true, tiempo.getTempMax() == tiempoRes.getTempMax());
        Assert.assertEquals(true, tiempo.getHumedad() == tiempoRes.getHumedad());
        Assert.assertEquals(true, tiempo.getViento() == tiempoRes.getViento());
        Assert.assertEquals(true, tiempo.getUbicacion().getNombre().equals(tiempoRes.getUbicacion().getNombre()));
        Assert.assertEquals(true, tiempo.getTipoDia().equals(tiempoRes.getTipoDia()));
    }

    @Test
    public void tiempoActualCoordenadas() throws LocationNotFoundException {

        Coordenadas ubi = new Coordenadas(4.32, 5.43);
        DatosMeteorologia tiempo = new DatosMeteorologia(ubi, LocalDate.now(), LocalTime.now(), "Despejado", 1, 2, 3, 4);
        new InsertarMeteorologiaBBDD().actualCoordenadas(tiempo);
        DatosMeteorologia tiempoRes = new SistemaFacade().obtenerTiempoHoyCoordenadas(ubi);

        Assert.assertEquals(true, tiempo.getTempMin() == tiempoRes.getTempMin());
        Assert.assertEquals(true, tiempo.getTempMax() == tiempoRes.getTempMax());
        Assert.assertEquals(true, tiempo.getHumedad() == tiempoRes.getHumedad());
        Assert.assertEquals(true, tiempo.getViento() == tiempoRes.getViento());
        Assert.assertEquals(true, tiempo.getUbicacion().getLatitud() == tiempoRes.getUbicacion().getLatitud());
        Assert.assertEquals(true, tiempo.getUbicacion().getLongitud() == tiempoRes.getUbicacion().getLongitud());
        Assert.assertEquals(true, tiempo.getTipoDia().equals(tiempoRes.getTipoDia()));
    }

    @Test
    public void tiempoXdiasCiudad() throws LocationNotFoundException {

        Ciudad ubi = new Ciudad("Madrid");
        DatosMeteorologia tiempo1 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(1), LocalTime.now(), "Despejado", 1, 2, 3, 4);
        DatosMeteorologia tiempo2 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(2), LocalTime.now(), "Despejado", 5, 6, 7, 8);
        List<DatosMeteorologia> lista = new ArrayList<DatosMeteorologia>();
        lista.add(tiempo1);
        lista.add(tiempo2);
        new InsertarMeteorologiaBBDD().previsionCiudad(lista);
        List<DatosMeteorologia> listaRes = new SistemaFacade().obtenerTiempoXdiasCiudad(ubi, 2);

        for (int i = 0; i < listaRes.size(); i++) {
            Assert.assertEquals(true, lista.get(i).getTempMin() == listaRes.get(i).getTempMin());
            Assert.assertEquals(true, lista.get(i).getTempMax() == listaRes.get(i).getTempMax());
            Assert.assertEquals(true, lista.get(i).getHumedad() == listaRes.get(i).getHumedad());
            Assert.assertEquals(true, lista.get(i).getViento() == listaRes.get(i).getViento());
            Assert.assertEquals(true, lista.get(i).getUbicacion().getNombre().equals(listaRes.get(i).getUbicacion().getNombre()));
            Assert.assertEquals(true, lista.get(i).getTipoDia().equals(listaRes.get(i).getTipoDia()));
        }
    }

    @Test
    public void tiempoXdiasCoordenadas() throws LocationNotFoundException {

        Coordenadas ubi = new Coordenadas(4.32, 5.43);
        DatosMeteorologia tiempo1 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(1), LocalTime.now(), "Despejado", 1, 1, 1, 1);
        DatosMeteorologia tiempo2 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(2), LocalTime.now(), "Despejado", 1, 1, 1, 1);
        List<DatosMeteorologia> lista = new ArrayList<DatosMeteorologia>();
        lista.add(tiempo1);
        lista.add(tiempo2);
        new InsertarMeteorologiaBBDD().previsionCoordenadas(lista);
        List<DatosMeteorologia> listaRes = new SistemaFacade().obtenerTiempoXdiasCoordenadas(ubi, 2);

        for (int i = 0; i < listaRes.size(); i++) {
            Assert.assertEquals(true, lista.get(i).getTempMin() == listaRes.get(i).getTempMin());
            Assert.assertEquals(true, lista.get(i).getTempMax() == listaRes.get(i).getTempMax());
            Assert.assertEquals(true, lista.get(i).getHumedad() == listaRes.get(i).getHumedad());
            Assert.assertEquals(true, lista.get(i).getViento() == listaRes.get(i).getViento());
            Assert.assertEquals(true, lista.get(i).getUbicacion().getLatitud() == listaRes.get(i).getUbicacion().getLatitud());
            Assert.assertEquals(true, lista.get(i).getUbicacion().getLongitud() == listaRes.get(i).getUbicacion().getLongitud());
            Assert.assertEquals(true, lista.get(i).getTipoDia().equals(listaRes.get(i).getTipoDia()));
        }
    }
}
