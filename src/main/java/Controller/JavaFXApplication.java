package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JavaFXApplication extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
        stage.setTitle("TiempoApp");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

        /*stage.getIcons().add(new Image("Resources/images/icon.png"));
        stage.setScene(new Scene(root, 1050, 670));
        stage.getScene().getStylesheets().addAll(getClass().getResource("Resources/styles/style.css").toExternalForm());
        stage.show();
        stage.setResizable(false);
        stage.sizeToScene();*/

    public static void main(String[] args) {
        launch(args);
    }
}
