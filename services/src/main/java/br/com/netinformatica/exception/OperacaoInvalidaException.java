package br.com.netinformatica.exception;

public class OperacaoInvalidaException extends RuntimeException {

    private static final long serialVersionUID = -4230025063997222496L;

    public OperacaoInvalidaException() {
        super("Operação inválida");
    }

    public OperacaoInvalidaException(String arg0) {
        super(arg0);
    }

}
