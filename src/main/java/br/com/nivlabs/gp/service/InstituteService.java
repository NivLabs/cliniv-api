package br.com.nivlabs.gp.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Institute;
import br.com.nivlabs.gp.models.domain.Parameter;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.CustomerInfoDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.InstituteDTO;
import br.com.nivlabs.gp.models.dto.LicenseDTO;
import br.com.nivlabs.gp.models.dto.ParameterDTO;
import br.com.nivlabs.gp.repository.InstituteRepository;
import br.com.nivlabs.gp.repository.ParameterRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Classe InstituteService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Service
public class InstituteService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InstituteRepository instituteRepo;

    @Autowired
    private ParameterRepository paramRepo;
    
    @Value("${secret-key.property}")
    private String secretKey;

    public InstituteDTO getSettings() {

        InstituteDTO response = new InstituteDTO();

        logger.info("Buscando informações de configurações...");
        List<Parameter> parameters = paramRepo.findAll();
        parameters.sort((primary, scondary) -> primary.getId().compareTo(scondary.getId()));

        logger.info("Buscando informações da instituição...");
        List<Institute> institutes = instituteRepo.findAll();

        if (!institutes.isEmpty()) {
            Institute institute = institutes.get(0);
            CustomerInfoDTO customer = new CustomerInfoDTO();
            AddressDTO address = new AddressDTO();
            LicenseDTO license = new LicenseDTO();
            BeanUtils.copyProperties(institute, address);
            BeanUtils.copyProperties(institute, customer);
            BeanUtils.copyProperties(institute, license);

            customer.setLogoBase64(institute.getCompanyLogo());
            customer.setAddress(address);
            customer.setLicense(license);
            response.setCustomerInfo(customer);
        }

        if (!parameters.isEmpty()) {
            for (Parameter parameter : parameters)
                response.getParameters().add(new ParameterDTO(parameter.getId(), parameter.getName(), parameter.getGroup(),
                        parameter.getMetaType(), parameter.getValue(),
                        parameter.getGroupValues() != null ? parameter.getGroupValues().split(";") : null));
        }

        return response;
    }

    public void setCompanyLogo(FileDTO file) {
        if (file == null || StringUtils.isNullOrEmpty(file.getBase64()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A propriedade de Base64 da imagem não pode ser nula");
        List<Institute> institutes = instituteRepo.findAll();
        if (!institutes.isEmpty()) {
            Institute institute = institutes.get(0);
            logger.info("Inserindo a Logo");
            institute.setCompanyLogo(file.getBase64());
            instituteRepo.save(institute);
            logger.info("Logo Inserida");
        }
    }
    
    public void setCustomerInfoCrypto(FileDTO file) {
        if (file == null || StringUtils.isNullOrEmpty(file.getBase64()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A propriedade de Base64 do arquivo não pode ser nula");
        
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			logger.error(e1.getMessage());
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível ler o arquivo de licença");
		}
        SecretKeySpec  key = new SecretKeySpec (this.secretKey.getBytes(), "AES");
        try {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e1) {
			logger.error(e1.getMessage());
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível ler o arquivo de licença");
		}
        
        String decrypted = null;
        byte[] decoded;
        logger.debug("Descryptografando arquivo");
        decoded = Base64.decodeBase64(file.getBase64().getBytes());
        try {
			decrypted = new String(cipher.doFinal(decoded));
		} catch (IllegalBlockSizeException | BadPaddingException e1) {
			logger.error(e1.getMessage());
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível ler o arquivo de licença");
		}
        logger.debug("Arquivo descryptografado");
        CustomerInfoDTO customerInfoDTO;
        logger.debug("Convertendo json em objeto");
        try {
        	customerInfoDTO = new ObjectMapper().readValue(decrypted, CustomerInfoDTO.class);
		} catch (JsonProcessingException e) {
			throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A propriedade de Base64 do arquivo não é um json válido");
		}
        logger.debug("Json convertido em objeto");
        InstituteDTO instituteDTO = getSettings();
        
        if (instituteDTO == null) {
        	Institute institute = new Institute();
        	BeanUtils.copyProperties(customerInfoDTO, institute);
        	logger.debug("Vai inserir objeto");
        	this.instituteRepo.save(institute);
        } else {
        	BeanUtils.copyProperties(customerInfoDTO, instituteDTO);
        	logger.debug("Vai inserir objeto");
        	persist(instituteDTO);
        }
        
    }
    
	public InstituteDTO persist(InstituteDTO instituteDTO) {
		logger.debug("persist");
		Institute institute = new Institute();
        CustomerInfoDTO customer = instituteDTO.getCustomerInfo();
        AddressDTO address = customer.getAddress();
        LicenseDTO license = customer.getLicense();
        BeanUtils.copyProperties(address, institute);
        BeanUtils.copyProperties(customer, institute);
        BeanUtils.copyProperties(license, institute);

        this.instituteRepo.save(institute);
		return instituteDTO;
	}
    
}
