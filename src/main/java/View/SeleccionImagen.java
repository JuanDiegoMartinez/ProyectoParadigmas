package View;

import java.time.LocalTime;

public class SeleccionImagen {

    public static String getImage(String icon, String tiempo) {
        String iconMinuscula = icon.toLowerCase();
        String hora = tiempo.substring(0, 2);
        int horaNum = Integer.parseInt(hora);
        if(horaNum>19 || horaNum < 6){
            iconMinuscula = iconMinuscula+"n";
        }else{
            iconMinuscula = iconMinuscula+"d";
        }

        switch (iconMinuscula){
            case("clearn"):
                return "images/clearNoche.png";
            case "cleard":
                return "images/soleado.png";
            case "cloudsd":
                return "images/02d.png";
            case "cloudsn":
                return "images/02n.png";
            case "fogn":
                return "images/niebla.png";
            case "fogd":
                return "images/niebla.png";
            case"mistn":
                return "images/niebla.png";
            case"mistd":
                return "images/niebla.png";
            case "raind":
                return "images/10d.png";
            case "extremed":
                return "images/10d.png";
            case "extremen":
                return "images/10n.png";
            case "rainn":
                return "images/10n.png";
            case "snown":
                return "images/nieve.png";
            case "snowd":
                return "images/nieve.png";
            default:
                return "images/oops.png";

        }
    }
}

