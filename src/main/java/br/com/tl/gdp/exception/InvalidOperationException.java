package br.com.tl.gdp.exception;

public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = -4230025063997222496L;

    public InvalidOperationException() {
        super("Operação inválida");
    }

    public InvalidOperationException(String arg0) {
        super(arg0);
    }

}
