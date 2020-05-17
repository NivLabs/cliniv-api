package br.com.tl.gdp.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4184493622311156201L;

	public ObjectNotFoundException(String chave, String tipoDaEntidade) {
		super("Objeto não encontrado! ID: " + chave + " Tipo: " + tipoDaEntidade);
	}

	public ObjectNotFoundException(String textoDoErro) {
		super(textoDoErro);
	}

	public ObjectNotFoundException(String textoDoErro, Throwable erro) {
		super(textoDoErro, erro);
	}
}
