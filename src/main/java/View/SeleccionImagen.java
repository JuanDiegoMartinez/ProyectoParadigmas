package View;

public class SeleccionImagen {

    public static String getImage(String icon) {
        String iconMinuscula = icon.toLowerCase();
        switch (iconMinuscula){
            case "clear":
                return "images/soleado.png";
            case "clouds":
                return "images/nublado.png";
            case "fog":
                return "images/niebla.png";
            case"mist":
                return "images/niebla.png";
            case "rain":
                return "images/lluvia.png";
            case "snow":
                return "images/nieve.png";
        }
        return "images/soleado.png";
    }
}

