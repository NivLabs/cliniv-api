package br.com.nivlabs.cliniv.service;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Institute;
import br.com.nivlabs.cliniv.models.domain.Institute_;
import br.com.nivlabs.cliniv.models.domain.Parameter;
import br.com.nivlabs.cliniv.models.dto.*;
import br.com.nivlabs.cliniv.repository.InstituteRepository;
import br.com.nivlabs.cliniv.repository.ParameterRepository;
import br.com.nivlabs.cliniv.util.EncryptUtils;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Classe InstituteService.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 30 de nov de 2019
 */
@Service
public class InstituteService implements BaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InstituteRepository instituteRepo;

    @Autowired
    private ParameterRepository paramRepo;
    @Autowired
    HttpServletRequest request;

    private static Map<String, Institute> CACHE_INSTITUTE_INFO = new HashMap<>();
    private static Long MILI_REPRESENTATION = null;

    public InstituteDTO getSettings() {

        InstituteDTO response = new InstituteDTO();
        logger.info("Buscando informações de configurações...");
        List<Parameter> parameters = paramRepo.findAll();
        parameters.sort((primary, scondary) -> primary.getId().compareTo(scondary.getId()));

        if ((CACHE_INSTITUTE_INFO.get(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY)) == null) || MILI_REPRESENTATION < System.currentTimeMillis()) {
            logger.info("Buscando informações da instituição para o cliente :: {}...",
                    request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY));
            CACHE_INSTITUTE_INFO.put(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY), instituteRepo.findById(1L).get());
            MILI_REPRESENTATION = System.currentTimeMillis() + 3600000;
        } else {
            logger.info("Dados da instituição em cache. Tempo da próxima carga :: "
                    + (((System.currentTimeMillis() - MILI_REPRESENTATION) / 1000) / 60) * -1 + " minuto(s)");
        }

        if (!request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY).isEmpty()) {
            Institute institute = CACHE_INSTITUTE_INFO.get(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY));
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
                response.getParameters()
                        .add(new ParameterDTO(parameter.getId(), parameter.getAlias(), parameter.getName(), parameter.getGroup(),
                                parameter.getMetaType(), parameter.getValue(),
                                parameter.getGroupValues() != null ? parameter.getGroupValues().split(";") : null));
        }

        return response;
    }

    public void setCompanyLogo(FileDTO file) {
        if (file == null || StringUtils.isNullOrEmpty(file.getBase64()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A propriedade de Base64 da imagem não pode ser nula");
        Optional<Institute> optionalRet = instituteRepo.findById(1L);
        if (optionalRet.isPresent()) {
            logger.info("Inserindo a Logo");
            Institute institute = optionalRet.get();
            institute.setCompanyLogo(file.getBase64());
            instituteRepo.save(institute);
            logger.info("Logo Inserida, limpando cache...");
            CACHE_INSTITUTE_INFO.remove(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY));
        }
    }

    public void checkAndActiveLicense(FileDTO file) {
        if (file == null || StringUtils.isNullOrEmpty(file.getBase64())) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A propriedade de Base64 da licença não pode ser nula");
        }
        CustomerInfoDTO customerInfoDTO;
        try {
            customerInfoDTO = new ObjectMapper().readValue(decryptInformationsFromFile(file), CustomerInfoDTO.class);
        } catch (JsonProcessingException e) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Licença inválida!");
        }
        logger.debug("Json convertido em objeto");
        InstituteDTO instituteDTO = getSettings();

        if (instituteDTO == null) {
            Institute institute = new Institute();
            BeanUtils.copyProperties(customerInfoDTO, institute);
            logger.debug("Inserindo objeto");
            this.instituteRepo.save(institute);
        } else {
            BeanUtils.copyProperties(customerInfoDTO, instituteDTO);
            logger.debug("Inserindo objeto");
            persist(instituteDTO);
        }

    }

    /**
     * Retira a criptografia do arquivo
     *
     * @param file
     * @return Informações da licença
     */
    private String decryptInformationsFromFile(FileDTO file) {
        try {
            logger.info("Iniciando descriptografia do arquivo de licença");
            String decryptedInformations = EncryptUtils.getInstance().decrypt(file.getBase64());
            logger.info("Informações reconhecidas :: {}", decryptedInformations);
            return decryptedInformations;
        } catch (InvalidKeyException e) {
            logger.error("Chave de criptografia inválida!", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Chave de criptografia inválida");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException
                 | BadPaddingException e) {
            logger.error("Falha ao tentar descriptografar arquivo!", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao tentar descriptografar arquivo!");
        }
    }

    public InstituteDTO persist(InstituteDTO instituteDTO) {
        logger.debug("Iniciando processo de persistência dos dados da instituição");
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

    /**
     * Cria ou atualiza informações cadastrais da instituição principal
     *
     * @param cutomerInfo informações do cliente
     */
    public void createOrUpdate(CustomerInfoDTO cutomerInfo) {
        logger.info("Iniciando processo de atualização de dados cadastrais da instituição principal");
        Institute entity;
        Optional<Institute> institute = instituteRepo.findById(1L);
        logger.info("Checando se já existe cadastro para a instituição principal...");

        if (institute.isPresent()) {
            logger.info("Cadastro encontrado, atualizando o mesmo...");
            entity = institute.get();
            CustomerInfoDTO customer = cutomerInfo;
            AddressDTO address = customer.getAddress();
            BeanUtils.copyProperties(address, entity, Institute_.ID);
            BeanUtils.copyProperties(customer, entity, Institute_.ID);
        } else {
            logger.info("Nenhum cadastro encontrado, iniciando um novo cadastro...");
            entity = new Institute();
            CustomerInfoDTO customer = cutomerInfo;
            AddressDTO address = customer.getAddress();
            BeanUtils.copyProperties(address, entity);
            BeanUtils.copyProperties(customer, entity);
        }
        logger.info("Iniciando processo de persistência");
        instituteRepo.saveAndFlush(entity);
        CACHE_INSTITUTE_INFO.put(request.getHeader(SecurityContextUtil.CUSTOMER_ID_HEADER_KEY), entity);
        logger.info("Dados cadastrais atualizados com sucesso!");
    }

}
