package br.com.nivlabs.cliniv.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * Classe CreatedResourceEvent.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
public class CreatedResourceEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1155275871912052929L;

    private HttpServletResponse response;
    private Long codigo;

    public CreatedResourceEvent(Object object, HttpServletResponse response, Long codigo) {
        super(object);
        this.response = response;
        this.codigo = codigo;
    }

    public CreatedResourceEvent(Object source) {
        super(source);
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreatedResourceEvent other = (CreatedResourceEvent) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CreatedResourceEvent [response=" + response + ", codigo=" + codigo + "]";
    }

}