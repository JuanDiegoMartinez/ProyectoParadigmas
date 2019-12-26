package View;

public class SeleccionImagen {

    public static String getImage(String icon) {
        switch (icon){
            case "lluvia":
                return "/images/lluvia.png";
            case "soleado":
                return "/images/soleado.png";
        }
        return "images/01d.jpg";
    }
}
