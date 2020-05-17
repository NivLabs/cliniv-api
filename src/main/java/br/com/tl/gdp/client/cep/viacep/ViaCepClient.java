package br.com.tl.gdp.client.cep.viacep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.tl.gdp.client.RestClient;
import br.com.tl.gdp.exception.ExternalApiException;
import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.exception.ValidationException;

/**
 * Rest Client para a ViaCep API
 * 
 * @author viniciosarodrigues
 *
 */
public class ViaCepClient extends RestClient {

    private static final String baseUrl = "https://viaCEP.com.br/ws";
    private static Logger logger = LoggerFactory.getLogger(ViaCepClient.class);

    /**
     * Busca endereço por CEP na ViaCep API
     * 
     * @param cep
     * @return
     */
    public ViaCepAddress getAddressByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        logger.info("Iniciando busca por CEP na ViaCep API...");
        logger.info("Cep da busca :: {}", cep);
        ResponseEntity<ViaCepAddress> response = restTemplate.getForEntity(baseUrl.concat("/").concat(cep).concat("/json"),
                                                                           ViaCepAddress.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Endereço encontrado para o CEP {}", cep);
            return response.getBody();
        } else if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            logger.info("Não foi possível encontrado endereço para o CEP {}", cep);
            throw new ObjectNotFoundException("Cep não encontrado");
        } else if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            logger.warn("O CEP {} é inválido", cep);
            throw new ValidationException("CEP inválido, informe um CEP válido para a busca.");
        } else {
            logger.error("Falha na busca da API externa ViaCepAPI");
            throw new ExternalApiException("Falha ao buscar endereço na API Externa");
        }
    }
}
