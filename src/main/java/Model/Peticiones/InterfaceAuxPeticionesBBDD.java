package Model.Peticiones;

public interface InterfaceAuxPeticionesBBDD {

    public boolean comprobarFechayHoraCiudad(String ciudad);

    public boolean comprobarFechayHoraCoordenadas(double latitud, double longitud);

    public boolean comprobarFechaCiudad(String ciudad);

    public boolean comprobarFechaCoordenadas(double latitud, double longitud);
}
