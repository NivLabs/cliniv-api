package br.com.nivlabs.gp.integration.gtiss;

import java.net.URI;

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

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.integration.RestClient;
import br.com.nivlabs.gp.integration.RestResponsePage;

/**
 * Rest Client para a GTISS API
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 */
public class GTISSClient implements RestClient {

	
    private static final String PARAMETER_LOG = "Parâmetro de busca :: {}";

	private static final String FAIL = "Falha na busca da API externa GTISS";

	private static final String NOT_FOUND = "não encontrado";

	private static final String INVALID_PARAMETER = "{} é inválido";

	private static final String DESCRIPTION = "description";

	private static final String BASE_URL = "https://gestao-de-tiss.herokuapp.com/";
    
    private static Logger logger = LoggerFactory.getLogger(GTISSClient.class);
    
    /**
     * Busca medicamento por descrição na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<Medicine> getMedicineByDescription(String description, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por descrição na GTISS API...");
        logger.info(PARAMETER_LOG, description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine")
                .queryParam(DESCRIPTION, description);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<Medicine>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<Medicine>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Medicamento encontrado para a descrição {}", description);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), description);
        }
		return null;
    }
    
    private void handleException(HttpStatus statusCode, String parameter){
    	 if (statusCode.equals(HttpStatus.NOT_FOUND)) {
             logger.info("Não foi possível encontrar a descrição {}", parameter);
             throw new HttpException(HttpStatus.NOT_FOUND, NOT_FOUND);
         } else if (statusCode.equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
             logger.warn(INVALID_PARAMETER, parameter);
             throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, INVALID_PARAMETER);
         } else {
             logger.error(FAIL);
             throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, FAIL);
         }
	}

	/**
     * Busca medicamento por laboratório na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<Medicine> getMedicineByLaboratory(String laboratory, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por laboratório na GTISS API...");
        logger.info(PARAMETER_LOG, laboratory);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine/medicine/")
                .queryParam("laboratory", laboratory);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<Medicine>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<Medicine>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Medicamento encontrado para o laboratório {}", laboratory);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), laboratory);
        }
		return null;
    }
    
    /**
     * Busca medicamento por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Medicine getMedicineByCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por código na GTISS API...");
        logger.info(PARAMETER_LOG, code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine/".concat(code));
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Medicine> response = restTemplate.getForEntity(uri, Medicine.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Medicamento encontrado para o código {}", code);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), code);
        }
		return null;
    }
    
    /**
     * Busca procedimentos por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public MedicalProcedure getMedicalProcedureByCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de procedimentos por código na GTISS API...");
        logger.info(PARAMETER_LOG, code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/procedures/".concat(code));
                
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<MedicalProcedure> response = restTemplate.getForEntity(uri, MedicalProcedure.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Procedimento encontrado para o código {}", code);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), code);
        }
		return null;
    }
    
    /**
     * Busca procedimentos por descrição na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<MedicalProcedure> getMedicalProcedureByDescription(String description, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de procedimentos por descrição na GTISS API...");
        logger.info(PARAMETER_LOG, description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/procedures")
                .queryParam(DESCRIPTION, description);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<MedicalProcedure>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<MedicalProcedure>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("procedimento encontrado para a descrição {}", description);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), description);
        }
		return null;
    }
    
    /**
     * Busca taxas por código na GTISS API
     * 
     * @param code
     * @return
     */
    public Fee getFeeByCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de taxas por código na GTISS API...");
        logger.info(PARAMETER_LOG, code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/fee/".concat(code));

        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Fee> response = restTemplate.getForEntity(uri, Fee.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Taxa encontrada para o código {}", code);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), code);
        }
		return null;
    }
    
    /**
     * Busca taxas por descrição na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<Fee> getFeeByDescription(String description, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de taxas por descrição na GTISS API...");
        logger.info(PARAMETER_LOG, description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/fee")
                .queryParam(DESCRIPTION, description);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<Fee>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<Fee>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Taxa encontrada para a descrição {}", description);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), description);
        }
		return null;
    }
    
    /**
     * Busca materiais por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Material getMaterialByCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de materiais por código na GTISS API...");
        logger.info(PARAMETER_LOG, code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/material/".concat(code));

        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Material> response = restTemplate.getForEntity(uri, Material.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Material encontrado para o código {}", code);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), code);
        }
		return null;
    }
    
    /**
     * Busca materiais por descrição na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<Material> getMaterialByDescription(String description, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de materiais por descrição na GTISS API...");
        logger.info(PARAMETER_LOG, description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/material")
                .queryParam(DESCRIPTION, description);
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<RestResponsePage<Material>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<RestResponsePage<Material>>() {});

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("material encontrado para a descrição {}", description);
            return response.getBody();
        } else {
        	handleException(response.getStatusCode(), description);
        }
		return null;
    }
}
