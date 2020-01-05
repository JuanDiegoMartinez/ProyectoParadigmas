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

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class DatosVista implements Initializable {
    @FXML
    private Tab dia1,dia2,dia3,dia4;
    @FXML
    private Label informador;
    @FXML
    private Button busquedaCiudadHoy,busquedaCoordHoy,busquedaPrediccionCiudad,busquedaPrediccionCoord, anadirFavoritos, subirFavorito,bajarFavorito,modificarTag;
    @FXML
    ImageView imgCielo;
    @FXML
    private TextField txtCiudad,txtLatitud,txtLongitud, txtDias;
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
    private ObservableMap<String, ObservableList<DatosMeteorologia> >listaPredicciones;
    private ObservableMap<String, DatosMeteorologia > listaTiempoActual;

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
    }

    //consulta el tiempo de un dia en una ciudad
    @FXML
    public void vistaCiudadHoy(ActionEvent event) throws LocationNotFoundException {
        resetearTablas();
        if (txtCiudad.getText().length()>1) {                                       // te aseguras que haya informacion en el txtfield ciudad.
            Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
            try {                                                                   // pide los datos y actualiza base.
                DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCiudad(ciudad);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                listaTiempoActual.put(ciudad.toString(), resultado);
                ObservableList<DatosMeteorologia> listaAux = FXCollections.observableArrayList();
                listaAux.add(listaTiempoActual.get(ciudad.toString()));
                tblTiempo.setItems(listaAux);
                informador.setText("El tiempo en " + resultado.getUbicacion().toString() + ":");
                Image imagen = new Image(SeleccionImagen.getImage(resultado.getTipoDia()));
                imgCielo.setImage(imagen);
                setearDias(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La ciudad "+txtCiudad.getText()+" no encontrada");//
            }
        }else{
            informador.setText("para consultar el tiempo de una ciudad, introduce un valor en el campo ciudad.");
        }
    }
    //consulta el tiempo de un dia en una coordenada
    @FXML
    public void vistaCoordenadasHoy(ActionEvent event) throws LocationNotFoundException  {
        resetearTablas();
        if (txtLatitud.getText().length()>1 && txtLongitud.getText().length()>1) {                      // Compruebas que Latitud y longitud no estan vacios
            Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
            try {                                                                                       // pide los datos y actualiza base.
                DatosMeteorologia resultado = Controller.GestorPeticiones.obtenerTiempoHoyCoordenadas(coordenadas);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                          listaTiempoActual.put(resultado.getUbicacion().toString(), resultado);        // cuando son coordenadas guardamos por latitud long
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.add(listaTiempoActual.get(resultado.getUbicacion().toString()));
                tblTiempo.setItems(listaAux);
                informador.setText("El tiempo en "+resultado.getUbicacion().toString()+":");
                Image imagen = new Image(SeleccionImagen.getImage(resultado.getTipoDia()));
                imgCielo.setImage(imagen);
                setearDias(listaAux);

            }catch (LocationNotFoundException ex){
                informador.setText("La coordenada: "+"Latitud:"+txtLatitud.getText()+"Longitud:"+txtLongitud.getText()+ "no existe");//
            }
        }else{
            informador.setText("para consultar el tiempo de una ciudad, introduce un valor en el campo ciudad.");
        }
    }
    //consulta el tiempo de una prediccion en una ciudad
    @FXML
    public void vistaDiasCiudad(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        resetearTablas();
        if (txtCiudad.getText().length()>1) {                                                    // te aseguras que haya informacion en el txtfield ciudad.
            Ciudad ciudad = new Ciudad(this.txtCiudad.getText());
            try {                                                                                // pide los datos y actualiza base.
                List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCiudad(ciudad, 5);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.addAll(resultado);
                listaPredicciones.put(ciudad.toString(), listaAux);
                mostrarPorDias(listaAux);
                setearDias(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La ciudad "+txtCiudad.getText()+" no encontrada");//
            }
        }else{
            informador.setText("para consultar el tiempo de una ciudad, introduce un valor en el campo ciudad.");
        }
    }
    //consulta el tiempo de una prediccion en una coordenada
    public void vistaDiasCoordenadas(ActionEvent event) throws LocationNotFoundException, ActionPerformedException {
        resetearTablas();
        if (txtLatitud.getText().length()>1 && txtLongitud.getText().length()>1) {                  //Compruebas que Latitud y longitud no estan vacios
            Coordenadas coordenadas = new Coordenadas(Double.parseDouble(this.txtLatitud.getText()),  Double.parseDouble(this.txtLongitud.getText()));
            try {                                                                                   // pide los datos y actualiza base.
                List<DatosMeteorologia> resultado = Controller.GestorPeticiones.obtenerTiempoXdiasCoordenadas(coordenadas,5);
                if(resultado==null){
                    informador.setText("Servidor no disponible");//
                }
                ObservableList<DatosMeteorologia> listaAux= FXCollections.observableArrayList();
                listaAux.addAll(resultado);
                listaPredicciones.put(coordenadas.toString(), listaAux);
                mostrarPorDias(listaAux);
                setearDias(listaAux);
            }catch (LocationNotFoundException ex){
                informador.setText("La coordenada: "+"Latitud:"+txtLatitud.getText()+"Longitud:"+txtLongitud.getText()+ "no existe");//
            }
        }else{
            informador.setText("para consultar el tiempo de una ciudad, introduce un valor en el campo ciudad.");
        }
    }

    //añade el objeto que se encuentra en la tableview a favoritos (Listview).
    public void vistaAnadirFavoritos(ActionEvent event) throws LocationNotFoundException {
        ObservableList<DatosMeteorologia> tablaSeteada = tblTiempo.getItems();
        if(tablaSeteada.size()==0 || tblTiempo.getItems() == null) {                   // ------------->>> si no hay objetos seteados en la tableview
            informador.setText("No has elegido ninguna ciudad/coordenada como favorito");
        }else if(favoritos.contains(tablaSeteada.get(0).getUbicacion().toString())){   // ------------->>> comprueba que no este ya en favoritos
                informador.setText("Ya se encuentra "+tablaSeteada.get(0).getUbicacion().toString()+ " como favorito");
        }else if(tablaSeteada.get(0).getUbicacion() instanceof Coordenadas) {          // ------------->>> si es coordenada salta ventana emergente.
                Tag tag = TagBox.display();
                if(tag.isTag() && !alias.containsKey(tag.getTag())) {                   // --------------->>> ventana emergente seleccionamos aceptar y no hay tag
                    alias.put(tag.getTag(),tablaSeteada.get(0).getUbicacion().toString());
                    if(tablaSeteada.size()>1){                                         // ---------------->>> es prediccion
                        for(DatosMeteorologia dato: listaPredicciones.get(tablaSeteada.get(0).getUbicacion().toString())){ // actualizamos etiqueta para cada objeto DatosMeteorologia
                            dato.getUbicacion().setEtiqueta(tag.getTag());
                        }
                    }else if(tablaSeteada.size()==1){                                  // ------------------>> es solo 1 objeto DatosMeteorologia
                        tablaSeteada.get(0).getUbicacion().setEtiqueta(tag.getTag());
                    }
                    favoritos.add(tag.getTag());
                    listaFavoritos.setItems(favoritos);
                }else if(tag.isTag() && alias.containsKey(tag.getTag())){
                    informador.setText("ya se encuentra utilizado ese alias.");
                }else if (!tag.isTag()){                                                // --------------->>> ventana emergente seleccionamos cancelar
                    favoritos.add(tablaSeteada.get(0).getUbicacion().toString());
                    listaFavoritos.setItems(favoritos);
                }
        }else{
            favoritos.add(tablaSeteada.get(0).getUbicacion().toString());
            listaFavoritos.setItems(favoritos);
        }
    }

    //carga el objeto de la listView a la tableView
    public void seleccionarFavoritos(MouseEvent event){
        resetearTablas();
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        ObservableList<DatosMeteorologia> listaAux = FXCollections.observableArrayList();
        if(seleccionado ==null) {
            informador.setText("Favoritos está vacia");
        }else if(alias.containsKey(seleccionado)) {          // es un alias
            if(listaPredicciones.containsKey(alias.get(seleccionado))){        //mira si es pronostico
                listaAux = listaPredicciones.get(alias.get(seleccionado));
                mostrarPorDias(listaAux);
            }else if(listaTiempoActual.containsKey(alias.get(seleccionado))) { //entonces es 1dia
                listaAux.add(listaTiempoActual.get(alias.get(seleccionado)));
                tblTiempo.setItems(listaAux);
                listaTiempoActual.get(seleccionado).getTipoDia();
                Image imagen = new Image(SeleccionImagen.getImage(listaTiempoActual.get(seleccionado).getTipoDia()));
                imgCielo.setImage(imagen);
            }
        }else if(listaPredicciones.containsKey(seleccionado)) { //mira si es pronostico
            listaAux = listaPredicciones.get(seleccionado);
            mostrarPorDias(listaAux);

        }else if(listaTiempoActual.containsKey(seleccionado)) { //entonces es 1dia
            listaAux.add(listaTiempoActual.get(seleccionado));
            tblTiempo.setItems(listaAux);
            Image imagen = new Image(SeleccionImagen.getImage(listaTiempoActual.get(seleccionado).getTipoDia()));
            imgCielo.setImage(imagen);
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
        }else{
            if(alias.containsKey(seleccionado)){
                alias.remove(seleccionado);
            }
            favoritos.remove(seleccionado);
            listaFavoritos.setItems(favoritos);
        }
    }
    //ordena la Listview por orden ascendente
    public void ordenarFavoritosAsc(ActionEvent actionEvent) {
        FXCollections.sort(favoritos);
    }
    //ordena la Listview por orden descendente.
    public void ordenarFavoritosDes(ActionEvent actionEvent) {
        FXCollections.reverse(favoritos);
    }
    public void modificarTag(ActionEvent actionEvent) {
        String seleccionado = listaFavoritos.getSelectionModel().getSelectedItem();
        if(alias.containsKey(seleccionado)){ // ya es un tag
            Tag tag = TagBox.display();
            if(tag.isTag() && !alias.containsKey(tag.getTag())){                           //era ya un tag si quiere cambiarlo y no esta el alias usado
                favoritos.set(favoritos.indexOf(seleccionado), tag.getTag());
                listaFavoritos.setItems(favoritos);
                String valor = alias.get(seleccionado);
                alias.remove(seleccionado);
                alias.put(tag.getTag(), valor);
                informador.setText("Alias cambiado, "+seleccionado+" paso a ser "+tag.getTag());
            }else if(tag.isTag() && alias.containsKey(tag.getTag())){
                informador.setText("El alias "+tag.getTag()+ " ya esta en uso");
            }
        }else if(listaPredicciones.containsKey(seleccionado) && listaPredicciones.get(seleccionado).get(0).getUbicacion() instanceof Coordenadas){
            Tag tag = TagBox.display();
            if(tag.isTag() && !alias.containsKey(tag.getTag())){                           //es una coordenada si quiere cambiarlo y no esta el alias usado
                favoritos.set(favoritos.indexOf(seleccionado), tag.getTag());
                listaFavoritos.setItems(favoritos);
                String valor = alias.get(seleccionado);
                alias.remove(seleccionado);
                alias.put(tag.getTag(), valor);
                informador.setText("Alias cambiado, "+seleccionado+" paso a ser "+tag.getTag());
            }
        }else if(listaTiempoActual.containsKey(seleccionado) && listaTiempoActual.get(seleccionado).getUbicacion() instanceof Coordenadas) {
            Tag tag = TagBox.display();
            if (tag.isTag() && !alias.containsKey(tag.getTag())) {                           //es una coordenada si quiere cambiarlo y no esta el alias usado
                favoritos.set(favoritos.indexOf(seleccionado), tag.getTag());
                listaFavoritos.setItems(favoritos);
                String valor = alias.get(seleccionado);
                alias.remove(seleccionado);
                alias.put(tag.getTag(), valor);
                informador.setText("Alias cambiado, " + seleccionado + " paso a ser " + tag.getTag());
            }
        }else{
                informador.setText("No puedes poner tag a ciudades, ni usar tags repetidos.");
        }

    }
    public void mostrarPorDias(List<DatosMeteorologia> consulta){
        int contador=0;
        List<List<DatosMeteorologia>> resultado= new ArrayList<>();
        for (int i=0; i<consulta.size(); i++){
            resultado.add(new ArrayList<DatosMeteorologia>());
        }
        for(DatosMeteorologia dato: consulta){
            if (dato.getHora().toString().equals("00:00"))
                contador++;
            if(contador > 0 && contador < 5)
                resultado.get(contador-1).add(dato);
        }
        ObservableList<DatosMeteorologia>[] lista = new ObservableList[4];
        for (int i=0 ;i<4; i++){
            lista[i]= FXCollections.observableArrayList();
            lista[i].addAll(resultado.get(i));
        }
        tblTiempo.setItems(lista[0]);
        tblTiempo1.setItems(lista[1]);
        tblTiempo2.setItems(lista[2]);
        tblTiempo3.setItems(lista[3]);
        informador.setText("El tiempo en "+consulta.get(0).getUbicacion().toString()+":");

    }
    //inicia columnas de la tableView
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
    }
    private void setearDias(List lista){
        if (lista.size()==1) {
            dia1.setText(tblTiempo.getItems().get(0).getDia().toString());
        }else{
            dia1.setText(tblTiempo.getItems().get(0).getDia().toString());
            dia2.setText(tblTiempo.getItems().get(1).getDia().toString());
            dia3.setText(tblTiempo.getItems().get(2).getDia().toString());
            dia4.setText(tblTiempo.getItems().get(3).getDia().toString());
        }
    }
}

