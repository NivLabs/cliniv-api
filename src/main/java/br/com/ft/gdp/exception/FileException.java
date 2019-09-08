package br.com.ft.gdp.exception;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 6695363618876886566L;

	public FileException(String msg) {
		super(msg);
	}

	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}