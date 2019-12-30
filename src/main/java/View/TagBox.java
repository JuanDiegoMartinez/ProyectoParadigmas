package View;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class TagBox {
    static String tag;
    static boolean answer;

    public static Tag display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Añadir nuevo tag");

        window.setMinWidth(250);
        Label label = new Label();
        label.setText("Si no anades un tag a la coordenada, se anadira un tag por defecto.");

        TextField txtTag = new TextField();
        Button yesButton = new Button("Aceptar");
        Button noButton = new Button("Cancelar");


        yesButton.setOnAction(e -> {
            tag = txtTag.getText();
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);

        //añadir botones
        layout.getChildren().addAll(label,txtTag, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //devolver resultado.
        return new Tag(answer,tag);
    }
}