package br.com.ft.gdp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.EventType;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.domain.Visit;
import br.com.ft.gdp.models.domain.VisitEvent;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.NewPatientVisitDTO;
import br.com.ft.gdp.models.dto.PatientInfoDTO;
import br.com.ft.gdp.models.dto.VisitDTO;
import br.com.ft.gdp.models.dto.VisitEventDTO;
import br.com.ft.gdp.models.dto.VisitInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.VisitEventRepository;
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
	private VisitEventRepository visitEventRepo;
	@Autowired
	private PatientService patientService;

	/**
	 * Busca histórico de Visitas por Paciente
	 * 
	 * @param patientId
	 * @return lista de VisitDTO
	 */
	public List<VisitDTO> getVisitsByPatientId(Long patientId) {
		List<Visit> listOfVisits = dao.findByPatient(new Patient(patientId));

		if (listOfVisits.isEmpty())
			throw new ObjectNotFoundException(String.format("Não existe visita para o paciente %s", patientId));

		List<VisitDTO> listOfVisitsDTO = new ArrayList<>();
		listOfVisits.forEach(visit -> listOfVisitsDTO.add(new VisitDTO(visit.getId(), visit.getDateTimeEntry(),
				visit.getReasonForEntry(), Boolean.valueOf(visit.getDateTimeExit() != null))));
		return listOfVisitsDTO;
	}

	/**
	 * Busca Visitas por Id
	 * 
	 * @param id
	 * @return VisitInfoDTO
	 */
	public VisitInfoDTO findInfoById(Long id) {
		Visit visitFromDb = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Visita com código %s não encontrada", id)));
		Person person = visitFromDb.getPatient().getPerson();
		VisitInfoDTO visitInfo = new VisitInfoDTO();
		BeanUtils.copyProperties(person, visitInfo);
		visitInfo.setId(visitFromDb.getId());
		visitInfo.setPatientId(visitFromDb.getPatient().getId());
		visitInfo.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

		List<VisitEvent> listOfEventsFromDb = visitEventRepo.findByVisitId(visitFromDb.getId());
		listOfEventsFromDb.forEach(event -> visitInfo.getEvents().add(new VisitEventDTO(event.getId(),
				event.getEventDateTime(), event.getEventType().getDescription(), event.getDocumentId())));
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
		auxEntity.setDateTimeExit(LocalDateTime.now());
		update(id, auxEntity);
	}

	/**
	 * Realiza a criação de uma visita de paciente à partir do DTO
	 * 
	 * @param visitDto
	 * @return Visit
	 */
	public VisitInfoDTO persistNewVisit(NewPatientVisitDTO visitDto) {
		VisitInfoDTO visit = null;
		try {
			visit = getActiveVisit(visitDto.getPatientId());

		} catch (ValidationException e) {

			PatientInfoDTO savedPatient = patientService.findByPateintId(visitDto.getPatientId());

			Visit convertedVisit = new Visit();
			convertedVisit.setReasonForEntry(visitDto.getEntryCause());
			convertedVisit.setPatient(new Patient(savedPatient.getId()));
			convertedVisit = persist(convertedVisit);
			createEntryEvent(convertedVisit, visitDto);
			visit = getActiveVisit(visitDto.getPatientId());

			return visit;
		}
		if (visit != null) {
			throw new ValidationException(String.format(
					"O paciente de código [%s] e nome [%s] já possui uma visita ativa, favor realizar a alta da visita para iniciar uma nova.",
					visit.getPatientId(), visit.getFirstName()));
		}
		return visit;
	}

	/**
	 * @param convertedVisit
	 */
	private void createEntryEvent(Visit convertedVisit, NewPatientVisitDTO request) {
		VisitEvent entryEvent = new VisitEvent();
		entryEvent.setPatient(new Patient(convertedVisit.getPatient().getId()));
		entryEvent.setEventDateTime(convertedVisit.getDateTimeEntry());
		entryEvent.setTitle(convertedVisit.getReasonForEntry());
		entryEvent.setVisit(new Visit(convertedVisit.getId()));

		if (request.getResponsibleId() != null)
			// Verificar Responsável
			entryEvent.setResponsible(new Responsible(request.getResponsibleId()));

		if (request.getEventTypeId() != 1L && request.getEventTypeId() != 2L && request.getEventTypeId() != 3L)
			throw new ValidationException(
					"O tipo de evento informado é inválido. Tipos de entradas esperados: Entrada de paciente (1, 2 ou 3)");
		// Verificar Tipo do evento
		entryEvent.setEventType(new EventType(request.getEventTypeId()));

		visitEventRepo.save(entryEvent);
	}

	/**
	 * @param documentType
	 * @param documentValue
	 * @return
	 */
	public List<VisitDTO> findByDocument(DocumentType documentType, String documentValue) {
		return null;
	}

	/**
	 * Realiza a busca de visita ativa por código de paciente
	 * 
	 * @return VisitInfoDTO
	 */
	public VisitInfoDTO getActiveVisit(Long patientId) {
		PatientInfoDTO patient = patientService.findByPateintId(patientId);
		Visit visitFromDb = dao.findByPatientAndDateTimeExitIsNull(new Patient(patient.getId()))
				.orElseThrow(() -> new ValidationException(String.format(
						"Nenhuma visita ativa encontrada para %s, inicie uma nova visita para o paciente.",
						patient.getFirstName())));
		VisitInfoDTO visitInfo = new VisitInfoDTO();
		BeanUtils.copyProperties(patient, visitInfo);
		visitInfo.setId(visitFromDb.getId());
		visitInfo.setPatientId(visitFromDb.getPatient().getId());
		visitInfo.setDocument(new DocumentDTO(patient.getDocument().getType(), patient.getDocument().getValue()));

		List<VisitEvent> listOfEventsFromDb = visitEventRepo.findByVisitId(visitFromDb.getId());
		listOfEventsFromDb.forEach(event -> visitInfo.getEvents().add(new VisitEventDTO(event.getId(),
				event.getEventDateTime(), event.getEventType().getDescription(), event.getDocumentId())));
		return visitInfo;
	}

}
