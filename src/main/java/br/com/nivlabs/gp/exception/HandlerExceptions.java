package br.com.nivlabs.gp.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.nivlabs.gp.models.exception.ErrorItem;

@ControllerAdvice
public class HandlerExceptions {

	private Logger logger = LoggerFactory.getLogger(HandlerExceptions.class);

	/**
	 * Captura erros não esperados
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(Throwable e, HttpServletRequest req) {
		logger.error("Falha interna não esperada :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "Falha interna", Arrays.asList(), e.getMessage(),
				req.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	/**
	 * Captura operações inválidas
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<StandardErrorSpring> operacaoInvalida(InvalidOperationException e, HttpServletRequest req) {
		logger.error("Operação inválida :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
				HttpStatus.METHOD_NOT_ALLOWED.value(), "Operação inválida", Arrays.asList(), e.getMessage(),
				req.getRequestURI());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
	}

	/**
	 * Captura tentativa de resgate de senha inválido
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(NewPasswordInvalidException.class)
	public ResponseEntity<StandardErrorSpring> newPasswordInvalidException(NewPasswordInvalidException e,
			HttpServletRequest req) {
		logger.error("Erro de validação no processo de Nova Senha :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Informações inválidas", Arrays.asList(),
				"Você não forneceu as informações necessárias para recuperar a senha", req.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * Trata exceções de propriedades inválidas
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(GenerateReportException.class)
	public ResponseEntity<StandardErrorSpring> generateReportException(GenerateReportException e,
			HttpServletRequest req) {
		logger.error("Falha ao tentar gerar relatório :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Propriedade não reconhecida", Arrays.asList(), e.getMessage(), req.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * Captura erros de padrões da API
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(HttpException.class)
	public ResponseEntity<StandardErrorSpring> validationException(HttpException e, HttpServletRequest req) {
		logger.error("Erro da API :: ", e.toString());
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), e.getStatus().value(),
				"Erro da API", Arrays.asList(), e.getMessage(), req.getRequestURI());

		return ResponseEntity.status(e.getStatus()).body(err);
	}

	/**
	 * Trata exceções de propriedades inválidas
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(HttpMessageNotReadableException e,
			HttpServletRequest req) {
		logger.error("Falha interna não esperada :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Propriedade não reconhecida", Arrays.asList(), getPropertyMessageError(e), req.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * Captura nome da propriedade inválida do corpo da requisição
	 * 
	 * @param e
	 * @return
	 */
	private String getPropertyMessageError(HttpMessageNotReadableException e) {
		String message = e.getMostSpecificCause().getMessage();
		message = message.substring(message.indexOf(" \"") + 2);
		message = message.substring(0, message.indexOf("\" "));
		return String.format("A propriedade [%s] é desconhecida", message);
	}

	/**
	 * Requisição má formada - BAD_REQUEST
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest req) {
		logger.error("Argumentos inválidos :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Requisição mal formada", getValidations(e), "Erro de validação", req.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * Trata a falta de permissão
	 * 
	 * @param e
	 * @param req
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(AccessDeniedException e,
			HttpServletRequest req) {
		logger.error("Acesso negado :: ", e);
		StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(),
				"Sem permissões", Arrays.asList(), "Seu usuário não tem permissão para acessar este recurso",
				req.getRequestURI());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private List<ErrorItem> getValidations(MethodArgumentNotValidException e) {
		List<ErrorItem> errors = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.add(new ErrorItem(error.getField(), error.getDefaultMessage()));
		}
		return errors;
	}

}