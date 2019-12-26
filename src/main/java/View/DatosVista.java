package View;

import Excepciones.ActionPerformedException;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class DatosVista implements Initializable {
    @FXML
    private Pane panel;
    @FXML
    private ScrollBar scroll;
    @FXML
    private Label informador;
    @FXML
    private Button busquedaCiudadHoy,busquedaCoordHoy,busquedaPrediccionCiudad,busquedaPrediccionCoord, anadirFavoritos;
    //private Service<Void> apagaBoton;
    @FXML
    ImageView imgCielo;
    @FXML
    private TextField txtCiudad,txtLatitud,txtLongitud, txtDias;
    @FXML
    private TableView<DatosMeteorologia> tblTiempo;
    @FXML
    private TableColumn colHora, colCielo, colHumedad, colViento, colMaxima, colMinima, colFoto;
    @FXML
    private ListView<String> listaFavoritos;

    private ObservableList<String> favoritos;
    private ObservableMap<String, ObservableList<DatosMeteorologia> >listaPredicciones;
    private ObservableMap<String, DatosMeteorologia > listaTiempoActual;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        panel = new Pane();
        scroll = new ScrollBar();
        listaTiempoActual = FXCollections.observableHashMap();
        listaPredicciones = FXCollections.observableHashMap();
        favoritos = FXCollections.observableArrayList();;


        this.colHora.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        //colHora.setMinWidth(tblTiempo.getMaxWidth()/2);
        this.colCielo.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
    }
    @FXML
    public void vistaCiudadHoy(ActionEvent event) throws LocationNotFoundException {
        Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
        DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
        listaTiempoActual.put(ciudad.toString(), resultado);
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.add(listaTiempoActual.get(ciudad.toString()));
        tblTiempo.setItems(listaAux);
        informador.setText("El tiempo en "+resultado.getUbicacion().toString()+":");
        Image imagen= new Image(SeleccionImagen.getImage(resultado.getTipoDia()));
        imgCielo.setImage(imagen);
    }
    @FXML
    public void vistaCoordenadasHoy(ActionEvent event) throws LocationNotFoundException  {
        Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
        DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
        listaTiempoActual.put(coordenadas.toString(), resultado);  // cuando son coordenadas guardamos por latitud long
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.add(listaTiempoActual.get(coordenadas.toString()));
        tblTiempo.setItems(listaAux);
        informador.setText("El tiempo en "+resultado.getUbicacion().toString()+":");
    }
    @FXML
    public void vistaDiasCiudad(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        if (txtDias.getText().isEmpty()){
            informador.setText("Para las predicciones es necesario introducir un dia entre 1 o 4 ");
            throw new ActionPerformedException("Para las predicciones es necesario introducir un dia entre 1 o 4 ");
        }
        int dias = Integer.parseInt(this.txtDias.getText());
        if(dias > 4 || dias < 1) {
            informador.setText("Solo se permiten previsiones entre 1 y 4 dias, introduce un valor correcto, gracias");
            throw new IllegalArgumentException("Solo se permiten previsiones entre 1 y 4 dias, introduce un valor correcto, gracias");
        }
        Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
        List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, dias);
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.addAll(resultado);
        listaPredicciones.put(ciudad.toString(), listaAux);
        tblTiempo.setItems(listaAux);
        informador.setText("El tiempo en "+resultado.get(0).getUbicacion().toString()+":");
    }
    public void vistaDiasCoordenadas(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        if (txtDias.getText().isEmpty()){
            informador.setText("Para las predicciones es necesario introducir un dia entre 1 o 4 ");
            throw new ActionPerformedException("Para las predicciones es necesario introducir un dia entre 1 o 4 ");
        }
        int dias = Integer.parseInt(this.txtDias.getText());
        if(dias > 4 || dias < 1) {
            informador.setText("Solo se permiten previsiones entre 1 y 4 dias, introduce un valor correcto, gracias");
            throw new IllegalArgumentException("Solo se permiten previsiones entre 1 y 4 dias, introduce un valor correcto, gracias");
        }
        Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
        String coordenadasTexto = coordenadas.toString();
        List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCoordenadas(coordenadas, dias);
        ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
        listaAux.addAll(resultado);
        listaPredicciones.put(coordenadasTexto, listaAux);
        tblTiempo.setItems(listaAux);
        informador.setText("El tiempo en "+resultado.get(0).getUbicacion().toString()+":");
    }
    public void vistaAnadirFavoritos(ActionEvent event) throws LocationNotFoundException {
        ObservableList<DatosMeteorologia> tablaSeteada = tblTiempo.getItems();
        if(tablaSeteada.size()==0 || tblTiempo.getItems() == null) {
            informador.setText("No has elegido ninguna ciudad/coordenada como favorito");
        }else if(favoritos.contains(tablaSeteada.get(0).getUbicacion().toString())){
                informador.setText("Ya se encuentra "+tablaSeteada.get(0).getUbicacion().toString()+ " como favorito");
        }else{
            favoritos.add(tablaSeteada.get(0).getUbicacion().toString());
            listaFavoritos.setItems(favoritos);
        }
    }

    public void seleccionarFavoritos(MouseEvent event){
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        ObservableList<DatosMeteorologia> listaAux = FXCollections.observableArrayList();
        if(seleccionado ==null){
            informador.setText("Favoritos está vacia");
        }else if(listaPredicciones.containsKey(seleccionado)) {
            listaAux = listaPredicciones.get(seleccionado);
            informador.setText("has seleccionado " + seleccionado);
            tblTiempo.setItems(listaAux);
        }else{
            listaAux.add(listaTiempoActual.get(seleccionado));
            informador.setText("has seleccionado " + seleccionado);
            tblTiempo.setItems(listaAux);
        }

    }

    public void vistaEliminarFavoritos(ActionEvent actionEvent) {
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        favoritos.remove(seleccionado);
        listaFavoritos.setItems(favoritos);
    }



    /*public void conTexto(ActionEvent event){
        apagaBoton = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        while(true)
                            if(txtCiudad.getText().length()>0){
                            busquedaCiudadHoy.setDisable(false);
                            }else{
                            busquedaCiudadHoy.setDisable(true);
                        }
                    }
                };
            }
        };
    }*/
}

