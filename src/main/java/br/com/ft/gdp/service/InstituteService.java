package br.com.ft.gdp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.models.domain.Institute;
import br.com.ft.gdp.models.dto.InstituteInfoDTO;
import br.com.ft.gdp.models.dto.SettingsDTO;
import br.com.ft.gdp.repository.InstituteRepository;

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

    /**
     * Busca informações da instituição
     * 
     * @return
     */
    public InstituteInfoDTO getInstitute() {
        logger.info("Buscando informações da instituição...");
        List<Institute> institutes = instituteRepo.findAll();

        InstituteInfoDTO instituteInfo = new InstituteInfoDTO();

        if (!institutes.isEmpty()) {
            BeanUtils.copyProperties(institutes.get(0), instituteInfo);
            logger.info("Informações encontradas para o CNPJ {} com nome de {}...", instituteInfo.getCnpj(), instituteInfo.getName());
        }

        return instituteInfo;
    }

    public SettingsDTO getSettings() {
        return null;
    }
}
