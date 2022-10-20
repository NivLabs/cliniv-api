package br.com.nivlabs.cliniv.service.alergy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.controller.filters.AllergyFilters;
import br.com.nivlabs.cliniv.models.dto.AllergyDTO;
import br.com.nivlabs.cliniv.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.alergy.business.SavePatientAllergiesBusinessHandler;
import br.com.nivlabs.cliniv.service.alergy.business.SearchAlergyBusinessHandler;

/**
 * Camada de serviços para Alergias
 * 
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Service
public class AllergyService implements BaseService {

    @Autowired
    SavePatientAllergiesBusinessHandler savePatientAllergiesBusinessHandler;
    @Autowired
    SearchAlergyBusinessHandler searchAlergyBusinessHandler;

    /**
     * Salva as alterações de alergias do paciente
     * 
     * @param patientId Identificador único do paciente
     * @param allergies Alergias à serem processadas e salvas no paciente
     */
    public void savePatientAllergies(Long patientId, PatientAllergiesDTO allergies) {
        savePatientAllergiesBusinessHandler.save(patientId, allergies);
    }

    /**
     * Realiza uma busca pagina por alergias
     * 
     * @param filters Filtro de busca
     * @param pageSettings Configurações de paginação
     * @return Página filtrada de alergias
     */
    public Page<AllergyDTO> getPage(AllergyFilters filters) {
        return searchAlergyBusinessHandler.getPage(filters);
    }

}
