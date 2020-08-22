package br.com.nivlabs.gp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
