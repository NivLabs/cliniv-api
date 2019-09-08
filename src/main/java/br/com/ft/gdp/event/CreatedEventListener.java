package br.com.ft.gdp.event;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Classe CreatedEventListener.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Component
public class CreatedEventListener implements ApplicationListener<CreatedResourceEvent> {

    @Override
    public void onApplicationEvent(CreatedResourceEvent createdResource) {
        HttpServletResponse response = createdResource.getResponse();
        Long resourceId = createdResource.getCodigo();
        adicionaLocation(response, resourceId);
    }

    private void adicionaLocation(HttpServletResponse response, Long resourceId) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(resourceId)
                .toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}