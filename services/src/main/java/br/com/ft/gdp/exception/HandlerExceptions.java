package br.com.ft.gdp.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptions {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(ObjectNotFoundException e,
			HttpServletRequest req) {
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Não Encontrado", e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	public ResponseEntity<StandardErrorSpring> operacaoInvalida(InvalidOperationException e, HttpServletRequest req) {
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(),
				"Operação inválida", e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}
}