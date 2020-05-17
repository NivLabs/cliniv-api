package br.com.tl.gdp.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe CreatedResourceEvent.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CreatedResourceEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1155275871912052929L;

    private HttpServletResponse response;
    private Long codigo;

    public CreatedResourceEvent(Object object, HttpServletResponse response, Long codigo) {
        super(object);
        this.response = response;
        this.codigo = codigo;
    }
}