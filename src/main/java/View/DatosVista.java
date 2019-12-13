package View;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;


public class DatosVista implements Initializable {
    @FXML
    private Button busquedaCiudadHoy,busquedaCoordHoy,busquedaPrediccionCiudad,busquedaPrediccionCoord;
    @FXML
    private TextField txtCiudad,txtLatitud,txtLongitud, txtDias;
    @FXML
    private TableView <DatosMeteorologia> tblTiempo;
    @FXML
    private TableColumn colHora, colCielo, colHumedad, colViento, colMaxima, colMinima;

    private ObservableMap<String, ObservableList<DatosMeteorologia> >listaPredicciones;
    private ObservableMap<String, DatosMeteorologia >listaTiempoActual;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        listaTiempoActual = FXCollections.observableHashMap();
        listaPredicciones = FXCollections.observableHashMap();

        this.colHora.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        this.colCielo.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
    }
    @FXML
    public void vistaCiudadHoy(ActionEvent event) {
        try{
            Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
            DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
            listaTiempoActual.put(ciudad.toString(), resultado);
        }catch (LocationNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void vistaCoordenadasHoy(ActionEvent event)  {
        try{
            Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
            DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
            String tag = resultado.getUbicacion().getEtiqueta();
            listaTiempoActual.put(tag, resultado);  // cuando son coordenadas guardamos por tag?
        }catch (LocationNotFoundException ex){
            ex.printStackTrace();
        }
    }
    @FXML
    public void vistaDiasCiudad(ActionEvent event) throws LocationNotFoundException {
        int dias = Integer.parseInt(this.txtDias.getText());
        if(dias > 5) {
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 5."); }
        Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
        List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, dias);
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.addAll(resultado);
        listaPredicciones.put(ciudad.toString(), listaAux);
        tblTiempo.setItems(listaAux);


    }
    public void vistaDiasCoordenadas(ActionEvent event) throws LocationNotFoundException {
        int dias = Integer.parseInt(this.txtDias.getText());
        if (dias > 5) {
            throw new IllegalArgumentException("El máximo de días para realizar la previsión son 5.");
        }
        Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
        List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCoordenadas(coordenadas, dias);
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.addAll(resultado);
        String tag = resultado.get(0).getUbicacion().getEtiqueta();
        listaPredicciones.put(tag, listaAux); // cuando son coordenadas guardamos por tag?
        tblTiempo.setItems(listaAux);
    }
}
