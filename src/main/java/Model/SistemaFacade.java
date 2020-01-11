package Model;

import Controller.GestorBBDD;
import Controller.GestorPeticiones;
import Controller.InterfaceGestorBBDD;
import Controller.InterfaceGestorPeticiones;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Datos.Ubicacion;
import Model.Peticiones.PeticionesEtiquetas;
import Model.Peticiones.PeticionesFavoritos;
import Model.Peticiones.PeticionesListaFavoritos;

import java.util.List;

public class SistemaFacade {

    private InterfaceGestorBBDD GestorBBDD;
    private InterfaceGestorPeticiones GestorPeticiones;
    private Model.Peticiones.PeticionesFavoritos PeticionesFavoritos;
    private Model.Peticiones.PeticionesListaFavoritos PeticionesListaFavoritos;
    private Model.Peticiones.PeticionesEtiquetas PeticionesEtiquetas;

    public SistemaFacade(InterfaceGestorBBDD g, InterfaceGestorPeticiones p) {
        GestorBBDD = g;
        GestorPeticiones = p;
    }

    public SistemaFacade() {
        GestorBBDD = new GestorBBDD();
        GestorPeticiones = new GestorPeticiones();
        PeticionesFavoritos = new PeticionesFavoritos();
        PeticionesListaFavoritos = new PeticionesListaFavoritos();
        PeticionesEtiquetas = new PeticionesEtiquetas();
    }

    //Obtener el estado del tiempo de hoy en una ciudad
    public DatosMeteorologia obtenerTiempoHoyCiudad(Ciudad ciudad) throws LocationNotFoundException {
        DatosMeteorologia resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoHoyCiudad(ciudad);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
        return resultado;
    }

    //Obtener el estado del tiempo de hoy en unas coordenadas
    public DatosMeteorologia obtenerTiempoHoyCoordenadas(Coordenadas coordenadas) throws LocationNotFoundException {
        DatosMeteorologia resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoHoyCoordenadas(coordenadas);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
        return resultado;
    }

    //Obtener el estado del tiempo de los próximos X días en una ciudad
    public List<DatosMeteorologia> obtenerTiempoXdiasCiudad(Ciudad ciudad, int dias) throws LocationNotFoundException {
        List<DatosMeteorologia> resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoXdiasCiudad(ciudad, dias);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, dias);
        return resultado;
    }

    //Obtener el estado del tiempo de los próximos X días en unas coordenadas
    public List<DatosMeteorologia> obtenerTiempoXdiasCoordenadas(Coordenadas coordenadas, int dias) throws LocationNotFoundException {
        List<DatosMeteorologia> resultado;

        //Primero miramos en la base de datos
        resultado = GestorBBDD.obtenerTiempoXdiasCoordenadas(coordenadas, dias);

        if (resultado != null) {
            return resultado;
        }

        //En caso de no estar hacemos la petición al servidor
        resultado = GestorPeticiones.obtenerTiempoXdiasCoordenadas(coordenadas, dias);
        return resultado;
    }

    //Añadir una ciudad a la lista de favoritos
    public boolean altaCiudadFavoritos(Ciudad ciudad) {
        return PeticionesFavoritos.anadirCiudad(ciudad);
    }

    //Borrar una ciudad de la lista de favoritos
    public boolean bajaCiudadFavoritos(Ciudad ciudad) {
        return PeticionesFavoritos.borrarCiudad(ciudad);
    }

    //Añadir unas coordenadas a la lista de favoritos
    public boolean altaCoordenadasFavoritos(String etiqueta, Coordenadas coordenadas) {
        return PeticionesFavoritos.anadirCoordenadas(etiqueta, coordenadas);
    }

    //Borrar unas coordenadas a la lista de favoritos
    public boolean bajaCoordenadasFavoritos(Coordenadas coordenadas) {
        return PeticionesFavoritos.borrarCoordenadas(coordenadas);
    }

    //Obtener la lista de favoritos
    public List<Ubicacion> obtenerListaFavoritos() {
        return PeticionesListaFavoritos.obtenerFavoritos();
    }

    //Modificar etiqueta
    public boolean modificarEtiqueta(String etiqueta, Coordenadas coordenadas) {
        return PeticionesEtiquetas.modificarEtiqueta(etiqueta, coordenadas);
    }

    //Geocodificación inversa
    public String obtenerEtiqueta(Coordenadas coordenadas) throws LocationNotFoundException {
        return GestorPeticiones.obtenerEtiqueta(coordenadas);
    }

    //Ordenar lista de favoritos
    public void ordenar(List<Ubicacion> ubicaciones) {
        PeticionesListaFavoritos.ordenar(ubicaciones);
    }
}