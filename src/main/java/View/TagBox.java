package View;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class TagBox {
    static String tag;
    static boolean respuesta;

    public static Tag display() {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Añadir nuevo tag");

        ventana.setMinWidth(250);
        Label label = new Label();
        label.setText("Si no anades un tag a la coordenada, se anadira un tag por defecto.");

        TextField txtTag = new TextField();
        Button aceptarBoton = new Button("Aceptar");
        Button cancelarBoton = new Button("Cancelar");


        aceptarBoton.setOnAction(e -> {
            tag = txtTag.getText();
            respuesta = true;
            ventana.close();
        });
        cancelarBoton.setOnAction(e -> {
            respuesta = false;
            ventana.close();
        });

        VBox layout = new VBox(10);

        //añadir botones
        layout.getChildren().addAll(label,txtTag, aceptarBoton, cancelarBoton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        ventana.setScene(scene);
        ventana.showAndWait();

        //devolver resultado.
        return new Tag(respuesta,tag);
    }
}