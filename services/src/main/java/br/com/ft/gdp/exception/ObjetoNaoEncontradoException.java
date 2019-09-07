package br.com.ft.gdp.exception;

public class ObjetoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 4184493622311156201L;

	public ObjetoNaoEncontradoException(String chave, String tipoDaEntidade) {
		super("Objeto não encontrado! ID: " + chave + " Tipo: " + tipoDaEntidade);
	}

	public ObjetoNaoEncontradoException(String textoDoErro) {
		super(textoDoErro);
	}

	public ObjetoNaoEncontradoException(String textoDoErro, Throwable erro) {
		super(textoDoErro, erro);
	}
}
