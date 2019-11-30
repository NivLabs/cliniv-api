package br.com.ft.gdp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Visit;
import br.com.ft.gdp.models.dto.NewVisitDTO;
import br.com.ft.gdp.models.dto.VisitDTO;
import br.com.ft.gdp.models.dto.VisitInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.VisitRepository;

/**
 * 
 * Classe VisitService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Service
public class VisitService implements GenericService<Visit, Long> {

    @Autowired
    private VisitRepository dao;
    @Autowired
    private PatientService patientService;

    /**
     * Busca histórico de Visitas por Paciente
     * 
     * @param patientId
     * @return
     */
    public List<VisitDTO> getVisitsByPatientId(Long patientId) {
        List<Visit> listOfVisits = dao.findByPatient(new Patient(patientId));

        return convert(listOfVisits);
    }

    /**
     * @param listOfVisits
     * @return
     */
    private List<VisitDTO> convert(List<Visit> listOfVisits) {
        return null;
    }

    public VisitInfoDTO findInfoById(Long id) {
        VisitInfoDTO visitInfo = new VisitInfoDTO();

        dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Visita com ID: [%s] não encontrado", id)));

        return visitInfo;
    }

    @Override
    public Visit update(Long id, Visit entity) {
        Visit auxEntity = findById(id);
        BeanUtils.copyProperties(entity, auxEntity, "id");
        return dao.save(auxEntity);
    }

    @Override
    public void delete(Visit entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Visit auxEntity = findById(id);
        dao.delete(auxEntity);

    }

    @Override
    public Visit persist(Visit entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    public void closeVisit(Long id) {
        Visit auxEntity = findById(id);
        auxEntity.setDateTimeExit(new Date());
        update(id, auxEntity);
    }

    /**
     * Realiza a criação de uma visita de paciente à partir do DTO
     * 
     * @param visitDto
     * @return
     */
    public Visit persistNewVisit(NewVisitDTO visitDto) {
        Patient savedPatient = patientService.findById(visitDto.getPatientId());

        Visit convertedVisit = new Visit();
        convertedVisit.setDateTimeEntry(new Date());
        convertedVisit.setReasonForEntry(visitDto.getReasonForEntry());
        convertedVisit.setPatient(savedPatient);

        return persist(convertedVisit);
    }

    /**
     * @param documentType
     * @param documentValue
     * @return
     */
    public List<VisitDTO> findByDocument(DocumentType documentType, String documentValue) {
        return null;
    }

}
