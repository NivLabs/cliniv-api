package br.com.ft.gdp.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ft.gdp.models.exception.ErrorItem;

@ControllerAdvice
public class HandlerExceptions {

    /**
     * Captura erros não esperados
     * 
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(Throwable e,
                                                                   HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Falha interna", Arrays.asList(), e.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    /**
     * Capura e trata erro de objetos não encontrados
     * 
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(ObjectNotFoundException e,
                                                                   HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Não Encontrado", Arrays.asList(), e.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
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
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Operação inválida", Arrays.asList(), e.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(HttpMessageNotReadableException e,
                                                                   HttpServletRequest req) {

        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Propriedade não reconhecida", Arrays.asList(), getPropertyMessageError(e),
                req.getRequestURI());

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
        return String.format("A propriedade [%s] é desconhecida",
                             message);
    }

    /**
     * Requisição má formada - BAD_REQUEST
     * 
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Requisição mal formada", getValidations(e), "Erro de validação", req.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
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