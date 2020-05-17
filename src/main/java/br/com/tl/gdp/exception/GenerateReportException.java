package br.com.tl.gdp.exception;

/**
 * Classe GenerateReportException.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 22 de abril de 2020
 */
public class GenerateReportException extends RuntimeException {

    private static final long serialVersionUID = 2091266293189074806L;

    public GenerateReportException(String msg) {
        super(msg);
    }

    public GenerateReportException(String msg, Throwable error) {
        super(msg, error);
    }
}
