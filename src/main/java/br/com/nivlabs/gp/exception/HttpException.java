package br.com.nivlabs.gp.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Erro padra da aplicação, usar apenas este erro para lançamento de qualquer
 * exceção.
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 6346915435058859173L;

	private HttpStatus status;
	private String message;

}
