package Excepciones;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
