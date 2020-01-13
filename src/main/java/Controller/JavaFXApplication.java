package Controller;

import View.DatosVista;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
        Parent root = loader.load();
        DatosVista controller = (DatosVista)loader.getController();
        stage.setTitle("TiempoApp");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        controller.requestCerrarAplicacion();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
