package br.com.ft.gdp.exception;

/**
 * Classe PatientHasExistsException.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
public class PatientHasExistsException extends RuntimeException {

    private static final long serialVersionUID = 2091266293189074806L;

    public PatientHasExistsException(String msg) {
        super(msg);
    }

    public PatientHasExistsException(String msg, Throwable error) {
        super(msg, error);
    }
}
