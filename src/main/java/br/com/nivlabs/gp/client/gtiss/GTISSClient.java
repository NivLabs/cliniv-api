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
     * Busca medicamento por descrição na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Page<Medicine> getMedicineByDescription(String description, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por descrição na GTISS API...");
        logger.info("Descrição da busca :: {}", description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine")
                .queryParam("description", description);
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
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar medicamento para a descrição {}", description);
            throw new HttpException(HttpStatus.NOT_FOUND, "medicamento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("A descrição {} é inválida", description);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "descrição inválida, informe uma descrição válida para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar medicamento na API Externa");
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
        logger.info("Laboratório da busca :: {}", laboratory);
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
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar medicamento para o laboratório {}", laboratory);
            throw new HttpException(HttpStatus.NOT_FOUND, "medicamento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("O laboratório {} é inválido", laboratory);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "laboratório inválido, informe um laboratório válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar medicamento na API Externa");
        }
    }
    
    /**
     * Busca medicamento por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Medicine getMedicineByCode(String code, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de medicamentos por código na GTISS API...");
        logger.info("Código da busca :: {}", code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/medicine/".concat(code));
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Medicine> response = restTemplate.getForEntity(uri, Medicine.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Medicamento encontrado para o código {}", code);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar medicamento para o código {}", code);
            throw new HttpException(HttpStatus.NOT_FOUND, "medicamento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("O código {} é inválido", code);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "código inválido, informe um código válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar medicamento na API Externa");
        }
    }
    
    /**
     * Busca procedimentos por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public MedicalProcedure getMedicalProcedureByCode(String code, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de procedimentos por código na GTISS API...");
        logger.info("Código da busca :: {}", code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/procedures/".concat(code));
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<MedicalProcedure> response = restTemplate.getForEntity(uri, MedicalProcedure.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Procedimento encontrado para o código {}", code);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar procedimento para o código {}", code);
            throw new HttpException(HttpStatus.NOT_FOUND, "procedimento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("O código {} é inválido", code);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "código inválido, informe um código válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar procedimento na API Externa");
        }
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
        logger.info("Descrição da busca :: {}", description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/procedures")
                .queryParam("description", description);
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
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar procedimento para a descrição {}", description);
            throw new HttpException(HttpStatus.NOT_FOUND, "procedimento não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("A descrição {} é inválida", description);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "descrição inválida, informe uma descrição válida para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar procedimento na API Externa");
        }
    }
    
    /**
     * Busca taxas por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Fee getFeeByCode(String code, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de taxas por código na GTISS API...");
        logger.info("Código da busca :: {}", code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/fee/".concat(code));
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Fee> response = restTemplate.getForEntity(uri, Fee.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Taxa encontrada para o código {}", code);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar taxa para o código {}", code);
            throw new HttpException(HttpStatus.NOT_FOUND, "taxa não encontrada");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("O código {} é inválido", code);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "código inválido, informe um código válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar taxa na API Externa");
        }
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
        logger.info("Descrição da busca :: {}", description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/fee")
                .queryParam("description", description);
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
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar taxa para a descrição {}", description);
            throw new HttpException(HttpStatus.NOT_FOUND, "taxa não encontrada");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("A descrição {} é inválida", description);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "descrição inválida, informe uma descrição válida para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar taxa na API Externa");
        }
    }
    
    /**
     * Busca materiais por código na GTISS API
     * 
     * @param descrição
     * @return
     */
    public Material getMaterialByCode(String code, Pageable pageSettings) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca de materiais por código na GTISS API...");
        logger.info("Código da busca :: {}", code);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/material/".concat(code));
                if (pageSettings !=null) {
                	targetUrl = targetUrl.queryParam("page", pageSettings.getPageNumber())
	                .queryParam("size", pageSettings.getPageSize())
	                .queryParam("sort", pageSettings.getSort());
                }
        
        URI uri = targetUrl.build().toUri();
                
        ResponseEntity<Material> response = restTemplate.getForEntity(uri, Material.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Material encontrado para o código {}", code);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar material para o código {}", code);
            throw new HttpException(HttpStatus.NOT_FOUND, "material não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("O código {} é inválido", code);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "código inválido, informe um código válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar material na API Externa");
        }
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
        logger.info("Descrição da busca :: {}", description);
        UriComponentsBuilder targetUrl= UriComponentsBuilder.fromUriString(BASE_URL)
                .path("/material")
                .queryParam("description", description);
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
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrar material para a descrição {}", description);
            throw new HttpException(HttpStatus.NOT_FOUND, "material não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY)) {
            logger.warn("A descrição {} é inválida", description);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "descrição inválida, informe uma descrição válida para a busca.");
        } else {
            logger.error("Falha na busca da API externa GTISS");
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao buscar material na API Externa");
        }
    }
}
