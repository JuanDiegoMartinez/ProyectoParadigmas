import Controller.GestorBBDD;
import Controller.GestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Peticiones.AuxPeticionesBBDD;
import Model.Peticiones.PeticionesBBDD;
import Model.SistemaFacade;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PruebasIntegracion {

    @Test
    public void datosMeteorologiaDiasCiudadError() throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad("Madrid");
        int dias = 2;
        GestorBBDD g = Mockito.mock(GestorBBDD.class);
        GestorPeticiones p = Mockito.mock(GestorPeticiones.class);
        SistemaFacade s = new SistemaFacade(g, p);
        Mockito.when(g.obtenerTiempoXdiasCiudad(ciudad, dias)).thenReturn(null);
        Mockito.when(p.obtenerTiempoXdiasCiudad(ciudad, dias)).thenReturn(null);
        List<DatosMeteorologia> resultado = s.obtenerTiempoXdiasCiudad(ciudad, dias);
        Assert.assertEquals(null, resultado);
    }

    @Test
    public void datosMeteorologiaDiasCoordenadasError() throws LocationNotFoundException {
        Coordenadas cor = new Coordenadas(23.32, 45.234);
        int dias = 2;
        GestorBBDD g = Mockito.mock(GestorBBDD.class);
        GestorPeticiones p = Mockito.mock(GestorPeticiones.class);
        SistemaFacade s = new SistemaFacade(g, p);
        Mockito.when(g.obtenerTiempoXdiasCoordenadas(cor, dias)).thenReturn(null);
        Mockito.when(p.obtenerTiempoXdiasCoordenadas(cor, dias)).thenReturn(null);
        List<DatosMeteorologia> resultado = s.obtenerTiempoXdiasCoordenadas(cor, dias);
        Assert.assertEquals(null, resultado);
    }

    @Test
    public void datosMeteorologiaDiasCiudadNoLlamadaServer() throws LocationNotFoundException {
        Ciudad ubi = new Ciudad("Madrid");
        DatosMeteorologia tiempo1 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(1), LocalTime.now(), "Despejado", 1, 2, 3, 4);
        DatosMeteorologia tiempo2 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(2), LocalTime.now(), "Despejado", 5, 6, 7, 8);
        List<DatosMeteorologia> lista = new ArrayList<DatosMeteorologia>();
        lista.add(tiempo1);
        lista.add(tiempo2);
        int dias = 2;

        GestorBBDD g = Mockito.mock(GestorBBDD.class);
        GestorPeticiones p = Mockito.mock(GestorPeticiones.class);
        SistemaFacade s = new SistemaFacade(g, p);
        Mockito.when(g.obtenerTiempoXdiasCiudad(ubi, dias)).thenReturn(lista);
        Mockito.verify(p, Mockito.never()).obtenerTiempoXdiasCiudad(ubi, dias);
        s.obtenerTiempoXdiasCiudad(ubi, dias);
    }

    @Test
    public void datosMeteorologiaDiasCoordenadasBuenoNoLlamadaServer() throws LocationNotFoundException {
        Coordenadas ubi = new Coordenadas(4.32, 5.43);
        DatosMeteorologia tiempo1 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(1), LocalTime.now(), "Despejado", 1, 1, 1, 1);
        DatosMeteorologia tiempo2 = new DatosMeteorologia(ubi, LocalDate.now().plusDays(2), LocalTime.now(), "Despejado", 1, 1, 1, 1);
        List<DatosMeteorologia> lista = new ArrayList<DatosMeteorologia>();
        lista.add(tiempo1);
        lista.add(tiempo2);
        int dias = 2;

        GestorBBDD g = Mockito.mock(GestorBBDD.class);
        GestorPeticiones p = Mockito.mock(GestorPeticiones.class);
        SistemaFacade s = new SistemaFacade(g, p);
        Mockito.when(g.obtenerTiempoXdiasCoordenadas(ubi, dias)).thenReturn(lista);
        Mockito.verify(p, Mockito.never()).obtenerTiempoXdiasCoordenadas(ubi, dias);
        s.obtenerTiempoXdiasCoordenadas(ubi, dias);
    }

    @Test
    public void noEstaCiudadBBDD() {
        Ciudad ciudad = new Ciudad("Madrid");
        AuxPeticionesBBDD a = Mockito.mock(AuxPeticionesBBDD.class);
        PeticionesBBDD p = new PeticionesBBDD(a);
        Mockito.when(a.comprobarFechayHoraCiudad(ciudad.getNombre())).thenReturn(false);
        ResultSet resultado = p.bbddCiudadHoy(ciudad);
        Assert.assertEquals(null, resultado);
    }

    @Test
    public void noEstaCoordenadasDiasBBDD() {
        Coordenadas cor = new Coordenadas(20.3, 34);
        int dias = 2;
        AuxPeticionesBBDD a = Mockito.mock(AuxPeticionesBBDD.class);
        PeticionesBBDD p = new PeticionesBBDD(a);
        Mockito.when(a.comprobarFechaCoordenadas(cor.getLatitud(), cor.getLongitud())).thenReturn(false);
        ResultSet resultado = p.bbddCoordenadasXdias(cor, dias);
        Assert.assertEquals(null, resultado);
    }
}
