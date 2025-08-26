package pe.gob.osinergmin.sicoes.util.common.exceptionHandler;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(Long id) {
        super("Registro no encontrado con id : "+id);
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}