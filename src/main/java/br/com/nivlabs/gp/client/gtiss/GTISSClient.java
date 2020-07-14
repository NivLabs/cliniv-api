package br.com.nivlabs.gp.client.gtiss;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.nivlabs.gp.client.RestClient;
import br.com.nivlabs.gp.client.RestResponsePage;
import br.com.nivlabs.gp.exception.HttpException;

/**
 * Rest Client para a GTISS API
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 */
public class GTISSClient implements RestClient {

	
    private static final String BASE_URL = "https://gestao-de-tiss.herokuapp.com/";
    
    private static Logger logger = LoggerFactory.getLogger(GTISSClient.class);
    
    /**
     * Busca medicamento por descrição na Viadescrição API
     * 
     * @param descrição
     * @return
     */
    public Page<Medicine> getMedicineByDescryption(String descryption, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por descrição na GTISS API...");
        logger.info("Descrição da busca :: {}", descryption);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine")
                .queryParam("description", descryption);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<Medicine>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<Medicine>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Medicamento encontrado para a descrição {}", descryption);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar medicamento para a descrição {}", descryption);
            throw new HttpException(HttpStatus.NOT_FOUND, "medicamento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("A descrição {} é inválida", descryption);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "descrição inválida, informe uma descrição válida para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar medicamento na API Externa");
        }
    }
    
    public static void main(String[] args) {
		GTISSClient gtissClient = new GTISSClient();
		Page<Medicine> page = gtissClient.getMedicineByDescryption("dipirona", null);
		
		List<Medicine> medicamentos = page.getContent();
		
		for (Medicine medicine : medicamentos) {
			System.out.println(medicine.toString());
		}
		
	}
}
