package br.com.netinformatica.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadraoSpring> objetoNaoEncontrado(ObjetoNaoEncontradoException e,
			HttpServletRequest req) {
		ErroPadraoSpring err = new ErroPadraoSpring(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Não Encontrado", e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	public ResponseEntity<ErroPadraoSpring> operacaoInvalida(OperacaoInvalidaException e, HttpServletRequest req) {
		ErroPadraoSpring err = new ErroPadraoSpring(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(),
				"Operação inválida", e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}
}