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
        ventana.setTitle("Nuevo tag");
        //ventana.initStyle(StageStyle.TRANSPARENT);      //quitar marcos

        Label label = new Label();
        label.setText("Si no introduces un tag, se anadira un tag por defecto.");

        TextField txtTag = new TextField();
        txtTag.setPrefWidth(130);
        txtTag.setMaxWidth(130);
        Button aceptarBoton = new Button("Aceptar");
        Button cancelarBoton = new Button("Cancelar");
        Button tercerBoton = new Button("Tercero");
        aceptarBoton.setTranslateX(-35);aceptarBoton.setTranslateY(25);
        cancelarBoton.setTranslateX(35);cancelarBoton.setTranslateY(-10);
        tercerBoton.setTranslateX(-110);tercerBoton.setTranslateY(-45);
        txtTag.setTranslateY(10);
        label.setTranslateY(10);

        tercerBoton.setOnAction(e -> {
            tag = txtTag.getText();
            respuesta = true;
            ventana.close();
        });

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
        layout.getChildren().addAll(label,txtTag, aceptarBoton, cancelarBoton,tercerBoton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        ventana.setMinHeight(100.0);
        ventana.setScene(scene);
        ventana.setMinHeight(200);
        ventana.setMinWidth(400);
        ventana.setResizable(false);

        ventana.showAndWait();


        //devolver resultado.
        return new Tag(respuesta,tag);
    }
}