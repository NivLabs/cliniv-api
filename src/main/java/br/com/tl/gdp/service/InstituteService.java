package br.com.tl.gdp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.models.domain.Institute;
import br.com.tl.gdp.models.domain.Parameter;
import br.com.tl.gdp.models.dto.AddressDTO;
import br.com.tl.gdp.models.dto.CustomerInfoDTO;
import br.com.tl.gdp.models.dto.InstituteDTO;
import br.com.tl.gdp.models.dto.LicenseDTO;
import br.com.tl.gdp.models.dto.ParameterDTO;
import br.com.tl.gdp.repository.InstituteRepository;
import br.com.tl.gdp.repository.ParameterRepository;

/**
 * 
 * Classe InstituteService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 30 de nov de 2019
 */
@Service
public class InstituteService implements GenericService<Institute, String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InstituteRepository instituteRepo;

    @Autowired
    private ParameterRepository paramRepo;

    public InstituteDTO getSettings() {

        InstituteDTO response = new InstituteDTO();

        logger.info("Buscando informações de configurações...");
        List<Parameter> parameters = paramRepo.findAll();

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

            customer.setAddress(address);
            customer.setLicense(license);
            response.setCustomerInfo(customer);
        }
        if (!parameters.isEmpty()) {
            parameters.forEach(parameter -> {
                response.getParameters().add(new ParameterDTO(parameter.getId(), parameter.getName(), parameter.getGroup(),
                        parameter.getMetaType(), parameter.getValue(), parameter.getGroupValues().split(";")));
            });
        }

        return response;
    }
}
