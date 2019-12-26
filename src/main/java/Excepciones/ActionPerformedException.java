package Excepciones;

public class ActionPerformedException extends Exception {
    public ActionPerformedException(String errorMessage) {
        super(errorMessage);
    }
}