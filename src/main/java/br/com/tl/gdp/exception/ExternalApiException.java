package br.com.tl.gdp.exception;

/**
 * Erro padrão para APIs externas
 * 
 * @author viniciosarodrigues
 *
 */
public class ExternalApiException extends RuntimeException {

    private static final long serialVersionUID = 5809761082567931126L;

    public ExternalApiException() {
        super("Erro de acesso à API externa");
    }

    public ExternalApiException(String arg0) {
        super(arg0);
    }

}
