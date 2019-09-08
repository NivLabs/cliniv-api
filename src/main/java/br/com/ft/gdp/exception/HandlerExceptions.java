package br.com.ft.gdp.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.ft.gdp.models.exception.ErrorItem;

@ControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardErrorSpring> objetoNaoEncontrado(ObjectNotFoundException e,
                                                                   HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Não Encontrado", e.getMessage(), req.getRequestURI(), Arrays.asList());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<StandardErrorSpring> operacaoInvalida(InvalidOperationException e, HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Operação inválida", e.getMessage(), req.getRequestURI(), Arrays.asList());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Requisição mal formada", "Erro de validação", req.getRequestURI(), getValidations(e));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * @param e
     * @param err
     */
    private List<ErrorItem> getValidations(MethodArgumentNotValidException e) {
        List<ErrorItem> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorItem(error.getField(), error.getDefaultMessage()));
        }
        return errors;
    }

}