package br.com.nivlabs.cliniv.exception;

import br.com.nivlabs.cliniv.models.exception.ErrorItem;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class HandlerExceptions {

    final private Logger logger = LoggerFactory.getLogger(HandlerExceptions.class);

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
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Falha interna", List.of(), e.getMessage(),
                req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
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
        logger.error("Erro da requisição", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), e.getStatus().value(),
                "Erro da requisição", List.of(), e.getMessage(), req.getRequestURI());

        return ResponseEntity.status(e.getStatus()).body(err);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<StandardErrorSpring> expiredJwtException(ExpiredJwtException e, HttpServletRequest req) {
        logger.error("Token expirado", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(),
                "Token expirado", List.of(), "Token expirado!", req.getRequestURI());

        return ResponseEntity.status(err.getStatus()).body(err);
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
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Propriedade não reconhecida", List.of(),
                getPropertyMessageError(e), req.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
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
        return String.format("A propriedade '%s' não é reconhecida", message);
    }

    /**
     * Erro de validação
     *
     * @param e
     * @param req
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorSpring> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                               HttpServletRequest req) {
        logger.error("Erro de validação :: ", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", getValidations(e),
                "Erro de validação", req.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
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
                "Sem permissões", List.of(), "Seu usuário não tem permissão para acessar este recurso",
                req.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorSpring> dataIntegrityViolationException(DataIntegrityViolationException e,
                                                                               HttpServletRequest req) {
        logger.error("Violação de Integridade :: ", e);
        StandardErrorSpring err = new StandardErrorSpring(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Violação de Integridade", List.of(), "Violação de integridade, esta ação não pode ser concluída",
                req.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    private List<ErrorItem> getValidations(MethodArgumentNotValidException e) {
        List<ErrorItem> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(new ErrorItem(error.getField(), error.getDefaultMessage()));
        }
        return errors;
    }

}