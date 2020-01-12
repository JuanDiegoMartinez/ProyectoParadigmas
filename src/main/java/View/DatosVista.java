package View;

import Controller.GestorPeticiones;
import Excepciones.ActionPerformedException;
import Excepciones.LocationNotFoundException;
import Model.Datos.Ciudad;
import Model.Datos.Coordenadas;
import Model.Datos.DatosMeteorologia;
import Model.Datos.Ubicacion;
import Model.SistemaFacade;
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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class DatosVista implements Initializable {
    @FXML
    private AnchorPane panel;
    @FXML
    private Tab dia1,dia2,dia3,dia4;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label informador;
    @FXML
    private Button busquedaCiudadHoy,busquedaCoordHoy,busquedaPrediccionCiudad,busquedaPrediccionCoord, anadirFavoritos, subirFavorito,bajarFavorito,modificarTag;
    @FXML
    ImageView imgCielo;
    @FXML
    private TextField txtCiudad,txtLatitud,txtLongitud;
    @FXML
    private TableView<DatosMeteorologia> tblTiempo,tblTiempo1,tblTiempo2,tblTiempo3;
    @FXML
    private TableColumn<DatosMeteorologia, LocalTime> colHora,colHora1,colHora2,colHora3;
    @FXML
    private TableColumn<DatosMeteorologia, String> colCielo,colCielo1,colCielo2,colCielo3;
    @FXML
    private TableColumn<DatosMeteorologia, Double> colHumedad,colViento,colMaxima,colMinima,colHumedad1,colViento1,colMaxima1,colMinima1;
    @FXML
    private TableColumn<DatosMeteorologia, Double> colHumedad2,colViento2,colMaxima2,colMinima2,colHumedad3,colViento3,colMaxima3,colMinima3;
    @FXML
    private ListView<String> listaFavoritos;
    @FXML
    private MenuButton ordenarFavoritos;
    @FXML
    private MenuItem ordenarFavoritosAsc, ordenarFavoritosDes;

    private HashMap<String,String> alias;
    private ObservableList<String> favoritos;
    private HashMap<String,Ubicacion> ubicaciones;
    private ObservableMap<String, ObservableList<DatosMeteorologia> >listaPredicciones;
    private ObservableMap<String, DatosMeteorologia > listaTiempoActual;
    private SistemaFacade SistemaFacade;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        listaTiempoActual = FXCollections.observableHashMap();
        listaPredicciones = FXCollections.observableHashMap();
        favoritos = FXCollections.observableArrayList();;
        ordenarFavoritos= new MenuButton();
        ordenarFavoritosAsc = new MenuItem();
        ordenarFavoritosDes = new MenuItem();
        ordenarFavoritos.getItems().addAll(ordenarFavoritosAsc,ordenarFavoritosDes);
        alias = new HashMap<>();
        iniciarColumnas();
        tabPane.getTabs().remove(dia2);
        tabPane.getTabs().remove(dia3);
        tabPane.getTabs().remove(dia4);

        SistemaFacade = new SistemaFacade();
        //Obtener la lista de favoritos de la BBDD al iniciar el programa
        //obtenerFavoritos();
    }
    

    public void requestCerrarAplicacion() {
        panel.getScene().getWindow().setOnCloseRequest((WindowEvent event1) -> {
            System.out.println("Cerrando aplicacion");
            //SistemaFacade.ordenar();
        });
    }

    //consulta el tiempo de un dia en una ciudad
    @FXML
    public void vistaCiudadHoy(ActionEvent event) throws LocationNotFoundException {
        resetearTablas();
        if (txtCiudad.getText().length()>1) {                                       // te aseguras que haya informacion en el txtfield ciudad.
            Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
            try {                                                                   // pide los datos y actualiza base.
                DatosMeteorologia resultado = SistemaFacade.obtenerTiempoHoyCiudad(ciudad);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                listaTiempoActual.put(ciudad.toString(), resultado);
                ObservableList<DatosMeteorologia> listaAux = FXCollections.observableArrayList();
                listaAux.add(listaTiempoActual.get(ciudad.toString()));
                tblTiempo.setItems(listaAux);
                tblTiempo.getSelectionModel().selectFirst();
                informador.setText("El tiempo en " + resultado.getUbicacion().toString() + ":");
                Image imagen = new Image(SeleccionImagen.getImage(resultado.getTipoDia(),resultado.getHora().toString()));
                imgCielo.setImage(imagen);
                setearDias(listaAux);
                limpiarTxtField(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La ciudad "+txtCiudad.getText()+" no encontrada");//
            }
        }else{
            informador.setText("Introduce el nombre de una ciudad valida");
        }
        if(tabPane.getTabs().size() == 4) {
            tabPane.getTabs().remove(dia2);
            tabPane.getTabs().remove(dia3);
            tabPane.getTabs().remove(dia4);
        }
        else if(tabPane.getTabs().size() == 0) {
            tabPane.getTabs().add(dia1);
        }
    }
    //consulta el tiempo de un dia en una coordenada
    @FXML
    public void vistaCoordenadasHoy(ActionEvent event) throws LocationNotFoundException  {
        resetearTablas();
        if (txtLatitud.getText().length()>1 && txtLongitud.getText().length()>1 && sonNumeros(txtLatitud.getText(),txtLongitud.getText())) {     // Compruebas que Latitud y longitud no estan vacios y son numeros
            Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
            try {                                                                                       // pide los datos y actualiza base.
                DatosMeteorologia resultado = SistemaFacade.obtenerTiempoHoyCoordenadas(coordenadas);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                listaTiempoActual.put(resultado.getUbicacion().toString(), resultado);        // cuando son coordenadas guardamos por latitud long
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.add(listaTiempoActual.get(resultado.getUbicacion().toString()));
                tblTiempo.setItems(listaAux);
                tblTiempo.getSelectionModel().selectFirst();
                informador.setText("El tiempo en "+resultado.getUbicacion().toString()+":");
                Image imagen = new Image(SeleccionImagen.getImage(resultado.getTipoDia(),resultado.getHora().toString()));
                imgCielo.setImage(imagen);
                setearDias(listaAux);
                limpiarTxtField(listaAux);

            }catch (LocationNotFoundException ex){
                informador.setText("La coordenada: "+"Latitud: "+txtLatitud.getText()+" Longitud: "+txtLongitud.getText()+ " no existe");//
            }
        }else{
            informador.setText("Introduce coordeandas validas");
        }
        if(tabPane.getTabs().size() == 4) {
            tabPane.getTabs().remove(dia2);
            tabPane.getTabs().remove(dia3);
            tabPane.getTabs().remove(dia4);
        } else if(tabPane.getTabs().size() == 0) {
            tabPane.getTabs().add(dia1);
        }
    }
    //consulta el tiempo de una prediccion en una ciudad
    @FXML
    public void vistaDiasCiudad(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        resetearTablas();
        if (txtCiudad.getText().length()>1) {                                                    // te aseguras que haya informacion en el txtfield ciudad.
            Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
            try {                                                                                // pide los datos y actualiza base.
                List<DatosMeteorologia> resultado = SistemaFacade.obtenerTiempoXdiasCiudad(ciudad, 5);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.addAll(resultado);
                System.out.println("Estoy en la vista: "+ listaAux.size());
                listaPredicciones.put(ciudad.toString(), listaAux);
                mostrarPorDias(listaAux);
                setearDias(listaAux);
                limpiarTxtField(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La ciudad "+txtCiudad.getText()+" no encontrada");//
            }
        }else{
            informador.setText("Introduce el nombre de una ciudad valida");
        }
        if(tabPane.getTabs().size() == 1) {
            tabPane.getTabs().add(dia2);
            tabPane.getTabs().add(dia3);
            tabPane.getTabs().add(dia4);
        } else if(tabPane.getTabs().size() == 0) {
            tabPane.getTabs().add(dia1);
            tabPane.getTabs().add(dia2);
            tabPane.getTabs().add(dia3);
            tabPane.getTabs().add(dia4);
        }
    }
    //consulta el tiempo de una prediccion en una coordenada
    public void vistaDiasCoordenadas(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        resetearTablas();
        if (txtLatitud.getText().length()>1 && txtLongitud.getText().length()>1 && sonNumeros(txtLatitud.getText(),txtLongitud.getText())) {       //Compruebas que Latitud y longitud no estan vacios y son numeros
            Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
            try {                                                                                                                         // pide los datos y actualiza base.
                List<DatosMeteorologia> resultado = SistemaFacade.obtenerTiempoXdiasCoordenadas(coordenadas,5);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.addAll(resultado);
                listaPredicciones.put(coordenadas.toString(), listaAux);
                mostrarPorDias(listaAux);
                setearDias(listaAux);
                limpiarTxtField(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La coordenada: "+"Latitud: "+txtLatitud.getText()+" Longitud: "+txtLongitud.getText()+ " no existe");//
            }
        }else{
            informador.setText("Introduce coordeandas validas");
        }
        if(tabPane.getTabs().size() == 1) {
            tabPane.getTabs().add(dia2);
            tabPane.getTabs().add(dia3);
            tabPane.getTabs().add(dia4);
        } else if(tabPane.getTabs().size() == 0) {
            tabPane.getTabs().add(dia1);
            tabPane.getTabs().add(dia2);
            tabPane.getTabs().add(dia3);
            tabPane.getTabs().add(dia4);
        }
    }

    //añade el objeto que se encuentra en la tableview a favoritos (Listview).
    public void vistaAnadirFavoritos(ActionEvent event) throws LocationNotFoundException {

        try {
            ObservableList<DatosMeteorologia> tablaSeteada = tblTiempo.getItems();

            if(tablaSeteada.get(0).getUbicacion().getNombre()!=null) {
                Ciudad ciudad = new Ciudad(tablaSeteada.get(0).getUbicacion().getNombre());
                ciudad.setEtiqueta(tablaSeteada.get(0).getUbicacion().getNombre());
                boolean esta = SistemaFacade.altaCiudadFavoritos(ciudad);
                if(esta){
                    favoritos.add(ciudad.getEtiqueta());
                    listaFavoritos.setItems(favoritos);
                    ubicaciones.put(ciudad.getEtiqueta(),  ciudad);
                }else{
                    informador.setText("Ya se encuentra "+tablaSeteada.get(0).getUbicacion().toString()+ " como favorito");
                }
            }else{
                String etiqueta = "(" + tablaSeteada.get(0).getUbicacion().getLatitud() + ", " +  tablaSeteada.get(0).getUbicacion().getLongitud() + ")";
                Coordenadas coordenada = new Coordenadas(tablaSeteada.get(0).getUbicacion().getLatitud(), tablaSeteada.get(0).getUbicacion().getLongitud());

                Tag tag = TagBox.display();

                if (tag.getRespuesta() != 0) {

                    if (tag.getRespuesta() == 1 && tag.getTag().length() > 0) {
                        etiqueta = tag.getTag();
                    }

                    else if (tag.getRespuesta() == 2) {
                        etiqueta = SistemaFacade.obtenerEtiqueta(coordenada);
                    }

                    boolean esta = SistemaFacade.altaCoordenadasFavoritos(etiqueta, coordenada);

                    if(esta){

                        coordenada.setEtiqueta(etiqueta);
                        favoritos.add(coordenada.getEtiqueta());
                        listaFavoritos.setItems(favoritos);
                        ubicaciones.put(coordenada.getEtiqueta(),  coordenada);

                    }else{
                        informador.setText("Ya se encuentran las coordenadas: ("+tablaSeteada.get(0).getUbicacion().getLatitud()+", "
                                +tablaSeteada.get(0).getUbicacion().getLongitud()+") como favorito");
                    }
                }
            }
        } catch (Exception e) {
            informador.setText("Antes de anadir a favoritos debes pedir la prevision.");
        }


    }

    //carga el objeto de la listView a la tableView
    public void seleccionarFavoritos(MouseEvent event){
        resetearTablas();
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        ObservableList<DatosMeteorologia> listaAux = FXCollections.observableArrayList();
        if(seleccionado ==null) {
            informador.setText("Favoritos esta vacio");
        }else if(alias.containsKey(seleccionado)) {          // es un alias
            if(listaPredicciones.containsKey(alias.get(seleccionado))){        //mira si es pronostico
                listaAux = listaPredicciones.get(alias.get(seleccionado));
                mostrarPorDias(listaAux);
                setearDias(listaAux);
                limpiarTxtField(listaAux);
            }else if(listaTiempoActual.containsKey(alias.get(seleccionado))) { //entonces es 1dia
                listaAux.add(listaTiempoActual.get(alias.get(seleccionado)));
                tblTiempo.setItems(listaAux);
                tblTiempo.getSelectionModel().selectFirst();
                listaTiempoActual.get(seleccionado).getTipoDia();
                Image imagen = new Image(SeleccionImagen.getImage(listaTiempoActual.get(seleccionado).getTipoDia(),listaTiempoActual.get(seleccionado).getHora().toString()));
                imgCielo.setImage(imagen);
                setearDias(listaAux);
                limpiarTxtField(listaAux);
            }
        }else if(listaPredicciones.containsKey(seleccionado)) { //mira si es pronostico
            listaAux = listaPredicciones.get(seleccionado);
            mostrarPorDias(listaAux);
            setearDias(listaAux);
            limpiarTxtField(listaAux);
        }else if(listaTiempoActual.containsKey(seleccionado)) { //entonces es 1dia
            listaAux.add(listaTiempoActual.get(seleccionado));
            tblTiempo.setItems(listaAux);
            tblTiempo.getSelectionModel().selectFirst();
            Image imagen = new Image(SeleccionImagen.getImage(listaTiempoActual.get(seleccionado).getTipoDia(),listaTiempoActual.get(seleccionado).getHora().toString()));
            imgCielo.setImage(imagen);
            setearDias(listaAux);
            limpiarTxtField(listaAux);
        }

    }
    //sube 1 posicion el objeto seleccionado en la Listview
    public void ordenarFavoritosSubir(ActionEvent actionEvent) {
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        int indexSeleccionado = favoritos.indexOf(seleccionado);
        if(seleccionado==null) {
            informador.setText("No tienes ningun favorito seleccionado, selecciona uno.");
        }else if(indexSeleccionado==0){
            informador.setText("Ya es el primero de la lista");
        }else {
            String aux = favoritos.get(indexSeleccionado - 1);
            favoritos.set(indexSeleccionado - 1, seleccionado);
            favoritos.set(indexSeleccionado, aux);
            listaFavoritos.setItems(favoritos);
            listaFavoritos.getSelectionModel().select(seleccionado);
        }
    }
    //baja 1 posicion el objeto seleccionado en la Listview
    public void ordenarFavoritosBajar(ActionEvent actionEvent) {
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        int indexSeleccionado = favoritos.indexOf(seleccionado);
        if(seleccionado==null) {
            informador.setText("No tienes ningun favorito seleccionado, selecciona uno.");
        }else if(indexSeleccionado == favoritos.size() - 1){
            informador.setText("Ya es el ultimo de la lista");
        }else {
            String aux = favoritos.get(indexSeleccionado + 1);
            favoritos.set(indexSeleccionado + 1, seleccionado);
            favoritos.set(indexSeleccionado, aux);
            listaFavoritos.setItems(favoritos);
            listaFavoritos.getSelectionModel().select(seleccionado);
        }
    }
    //elimina el objeto seleccionado en la Listview
    public void vistaEliminarFavoritos(ActionEvent actionEvent) {
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();

        if(seleccionado==null) {
            informador.setText("No tienes ningun favorito seleccionado, selecciona uno.");

        }else if(ubicaciones.containsKey(seleccionado)){

            System.out.println(ubicaciones.containsKey(seleccionado));
            System.out.println(seleccionado);

            if(ubicaciones.get(seleccionado).getNombre()!=null){
                Ciudad ciudad = new Ciudad(ubicaciones.get(seleccionado).getNombre());
                SistemaFacade.bajaCiudadFavoritos(ciudad);
            }
            else {
                Coordenadas coordenadas = new Coordenadas(ubicaciones.get(seleccionado).getLatitud(), ubicaciones.get(seleccionado).getLongitud());
                SistemaFacade.bajaCoordenadasFavoritos(coordenadas);
            }
            ubicaciones.remove(seleccionado);

        }
        favoritos.remove(seleccionado);
        listaFavoritos.setItems(favoritos);
    }
    //ordena la Listview por orden ascendente
    public void ordenarFavoritosAsc(ActionEvent actionEvent) {
        FXCollections.sort(favoritos);
    }
    //ordena la Listview por orden descendente.
    public void ordenarFavoritosDes(ActionEvent actionEvent) {
        FXCollections.reverse(favoritos);
    }

    public void modificarTag(ActionEvent actionEvent) throws LocationNotFoundException {

        try {
            String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
            Ubicacion ubi = ubicaciones.get(seleccionado);

            if (ubi.getNombre() != null) {
                informador.setText("No puedes poner tag a ciudades.");
            }
            else {
                String etiqueta = "(" + ubi.getLatitud() + ", " +  ubi.getLongitud() + ")";
                Coordenadas coordenada = new Coordenadas(ubi.getLatitud(), ubi.getLongitud());
                coordenada.setEtiqueta(seleccionado);

                Tag tag = TagBox.display();

                if (tag.getRespuesta() != 0) {
                    if (tag.getRespuesta() == 1 && tag.getTag().length() > 0) {
                        etiqueta = tag.getTag();
                    }

                    else if (tag.getRespuesta() == 2) {
                        etiqueta = SistemaFacade.obtenerEtiqueta(coordenada);
                    }

                    boolean esta = SistemaFacade.modificarEtiqueta(etiqueta, coordenada);

                    if (esta) {
                        coordenada.setEtiqueta(etiqueta);
                        favoritos.set(favoritos.indexOf(seleccionado), coordenada.getEtiqueta());
                        listaFavoritos.setItems(favoritos);
                        ubicaciones.remove(seleccionado);
                        ubicaciones.put(coordenada.getEtiqueta(),  coordenada);
                    }
                    else{
                        informador.setText("Ya se encuentran la etiqueta: "+etiqueta+" como favorito");
                    }
                }
            }
        } catch (Exception e) {
            informador.setText("No has seleccionado ningun favorito.");
        }


    }

    public void mostrarPorDias(List<DatosMeteorologia> consulta){
        int contador=0;
        List<List<DatosMeteorologia>> resultado= new ArrayList<>();
        for(DatosMeteorologia dato: consulta){
            if (dato.getHora().toString().equals("00:00")) {
                resultado.add(new ArrayList<DatosMeteorologia>());
                contador++;
            }
            if(contador > 0 && contador < 5)
                resultado.get(contador-1).add(dato);
        }
        ObservableList<DatosMeteorologia>[] lista = new ObservableList[4];

        for (int i=0 ;i<resultado.size()-1; i++){
            lista[i]= FXCollections.observableArrayList();
            lista[i].addAll(resultado.get(i));
        }

        tblTiempo.setItems(lista[0]);
        tblTiempo1.setItems(lista[1]);
        tblTiempo2.setItems(lista[2]);
        tblTiempo3.setItems(lista[3]);
        informador.setText("El tiempo en "+consulta.get(0).getUbicacion().toString()+":");
    }
    //inicia columnas de las tableViews
    private void iniciarColumnas(){
        this.colHora.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        this.colCielo.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
        this.colHora1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        this.colCielo1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima1.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
        this.colHora2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        this.colCielo2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima2.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
        this.colHora3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, LocalTime>("hora"));
        this.colCielo3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, String>("tipoDia"));
        this.colHumedad3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("humedad"));
        this.colViento3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("viento"));
        this.colMaxima3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMax"));
        this.colMinima3.setCellValueFactory(new PropertyValueFactory<DatosMeteorologia, Double>("tempMin"));
    }
    // restablece valores de las tablaview punteros, imagen de carga.
    private void resetearTablas(){
        ObservableList<DatosMeteorologia> nueva= FXCollections.observableArrayList();
        tblTiempo.setItems(nueva);
        tblTiempo1.setItems(nueva);
        tblTiempo2.setItems(nueva);
        tblTiempo3.setItems(nueva);
        imgCielo.setImage(null);
        dia1.setText("");
        dia2.setText("");
        dia3.setText("");
        dia4.setText("");
        dia1.getTabPane().getSelectionModel().select(0);
    }
    //setea en las tablaview en las tab el localdate de ese dato
    private void setearDias(List<DatosMeteorologia> lista){
        if (lista.size()==1) {
            dia1.setText(tblTiempo.getItems().get(0).getDia().toString());
        }else{
            dia1.setText(tblTiempo.getItems().get(0).getDia().toString());
            dia2.setText(tblTiempo1.getItems().get(1).getDia().toString());
            dia3.setText(tblTiempo2.getItems().get(2).getDia().toString());
            String a = tblTiempo3.getItems().get(3).getDia().toString();
            dia4.setText(a);
        }
    }
    //carga el icono del cielo para las tableview
    @FXML
    private void cargarImagenClick(MouseEvent event){
        DatosMeteorologia dato = tblTiempo.getSelectionModel().getSelectedItem();
        DatosMeteorologia dato1 = tblTiempo1.getSelectionModel().getSelectedItem();
        DatosMeteorologia dato2 = tblTiempo2.getSelectionModel().getSelectedItem();
        DatosMeteorologia dato3 = tblTiempo3.getSelectionModel().getSelectedItem();
        if(tblTiempo.isFocused()&& tblTiempo.getSelectionModel().getSelectedItem()!=null) {
            Image imagen = new Image(SeleccionImagen.getImage(dato.getTipoDia(),dato.getHora().toString()));
            imgCielo.setImage(imagen);
            informador.setText("El tiempo en " + dato.getUbicacion().toString() + ":");
        }else if(tblTiempo1.isFocused()&& tblTiempo1.getSelectionModel().getSelectedItem()!=null) {
            Image imagen = new Image(SeleccionImagen.getImage(dato1.getTipoDia(),dato1.getHora().toString()));
            imgCielo.setImage(imagen);
            informador.setText("El tiempo en " + dato1.getUbicacion().toString() + ":");
        }else if(tblTiempo2.isFocused()&& tblTiempo2.getSelectionModel().getSelectedItem()!=null) {
            Image imagen = new Image(SeleccionImagen.getImage(dato2.getTipoDia(),dato2.getHora().toString()));
            imgCielo.setImage(imagen);
            informador.setText("El tiempo en " + dato2.getUbicacion().toString() + ":");
        }else if(tblTiempo3.isFocused()&& tblTiempo3.getSelectionModel().getSelectedItem()!=null) {
            Image imagen = new Image(SeleccionImagen.getImage(dato3.getTipoDia(),dato3.getHora().toString()));
            imgCielo.setImage(imagen);
            informador.setText("El tiempo en " + dato3.getUbicacion().toString() + ":");
        }else{
            //informador.setText("Lista vacia");
        }
    }
    //comprueba que los inputs son numeros.
    public boolean sonNumeros(String latitud, String longitud){
        try{
            Double.parseDouble(latitud);
            Double.parseDouble(longitud);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    //limpia los txtfields de los tipos que no se usan
    public void limpiarTxtField(List<DatosMeteorologia> lista){
        if(lista.get(0).getUbicacion() instanceof  Coordenadas){
            Double latitud = ((Coordenadas) lista.get(0).getUbicacion()).getLatitud();
            Double longitud = ((Coordenadas) lista.get(0).getUbicacion()).getLongitud();
            txtLongitud.setText(longitud.toString()); txtLatitud.setText(latitud.toString());
            if(txtCiudad.getText().length()>0){
                txtCiudad.clear();
            }
        }else{
            txtCiudad.setText(lista.get(0).getUbicacion().toString());
            if(txtLatitud.getText().length()>0 || txtLongitud.getText().length()>0){
                txtLatitud.clear();txtLongitud.clear();
            }
        }
    }
}

