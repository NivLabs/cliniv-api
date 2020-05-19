package br.com.nivlabs.gp.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -4230025063997222496L;

    public ValidationException() {
        super("Erro de validação");
    }

    public ValidationException(String arg0) {
        super(arg0);
    }

}
