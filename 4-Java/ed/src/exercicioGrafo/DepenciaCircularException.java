package exercicioGrafo;

public class DepenciaCircularException extends Exception {
    public DepenciaCircularException(String msg) {
        super(msg);
    }
}
