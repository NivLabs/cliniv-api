package br.com.ft.gdp.exception;

/**
 * Classe NewPasswordInvalid.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
public class NewPasswordInvalidException extends RuntimeException {

    private static final long serialVersionUID = 2040523733485668754L;

    public NewPasswordInvalidException() {
        super();
    }

    public NewPasswordInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NewPasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewPasswordInvalidException(String message) {
        super(message);
    }

    public NewPasswordInvalidException(Throwable cause) {
        super(cause);
    }

}
