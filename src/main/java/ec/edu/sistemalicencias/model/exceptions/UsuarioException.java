package ec.edu.sistemalicencias.model.exceptions;

public class UsuarioException extends Exception {

    private static final long serialVersionUID = 1L;

    public UsuarioException(){super();}
    public UsuarioException(String message) {
        super(message);
    }
    public UsuarioException(String message, Throwable causa){super(message, causa);}
    public UsuarioException(Throwable causa){super(causa);}

}
