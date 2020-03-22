package br.com.ft.gdp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.NewResponsibleDTO;
import br.com.ft.gdp.models.dto.PersonInfoAddressDTO;
import br.com.ft.gdp.models.dto.ProfessionalIdentityDTO;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.models.dto.ResponsibleInfoDTO;
import br.com.ft.gdp.models.dto.SpecialityDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.ResponsibleRepository;
import br.com.ft.gdp.repository.SpecialityRepository;

/**
 * Classe ResponsibleService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Service
public class ResponsibleService {

	@Autowired
	private ResponsibleRepository dao;

	@Autowired
	private PersonService personService;
	@Autowired
	private SpecialityRepository specDao;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public Page<ResponsibleDTO> searchEntityPage(Pageable pageRequest) {
		Page<Responsible> pageOfResponsibles = dao.findAll(pageRequest);

		List<ResponsibleDTO> listOfResponsibleDTO = new ArrayList<>();

		pageOfResponsibles.forEach(responsible -> {
			ResponsibleDTO responsibleConverted = new ResponsibleDTO();
			responsibleConverted.setId(responsible.getId());
			BeanUtils.copyProperties(responsible.getPerson(), responsibleConverted, "id");
			listOfResponsibleDTO.add(responsibleConverted);
		});
		return new PageImpl<>(listOfResponsibleDTO, pageRequest, pageOfResponsibles.getTotalElements());
	}

	public ResponsibleInfoDTO findById(Long id) {
		Responsible responsibleFromDb = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));

		Person person = responsibleFromDb.getPerson();

		ResponsibleInfoDTO responsibleConverted = new ResponsibleInfoDTO();
		responsibleConverted.setId(responsibleFromDb.getId());
		BeanUtils.copyProperties(responsibleFromDb.getPerson(), responsibleConverted, "id");
		BeanUtils.copyProperties(responsibleFromDb, responsibleConverted);
		responsibleConverted.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

		if (person.getAddress() != null) {
			PersonInfoAddressDTO address = new PersonInfoAddressDTO();
			BeanUtils.copyProperties(person.getAddress(), address);
			responsibleConverted.setAddress(address);
		}
		if (responsibleFromDb.getProfessionalIdentity() != null) {
			ProfessionalIdentityDTO professionalIdentity = new ProfessionalIdentityDTO();
			professionalIdentity.setRegisterValue(responsibleFromDb.getProfessionalIdentity());
			if (responsibleFromDb.getInitialsIdentity() != null) {
				professionalIdentity.setRegisterType(responsibleFromDb.getInitialsIdentity());
			}
			responsibleConverted.setProfessionalIdentity(professionalIdentity);
		}

		responsibleConverted.setId(responsibleFromDb.getId());

		return responsibleConverted;
	}

	public ResponsibleInfoDTO findPersonByCpf(String cpf) {
		Person personFromDb = personService.findByCpf(cpf);

		ResponsibleInfoDTO responsibleInfo = new ResponsibleInfoDTO();
		BeanUtils.copyProperties(personFromDb, responsibleInfo, "id");
		responsibleInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

		if (personFromDb.getAddress() != null) {
			PersonInfoAddressDTO address = new PersonInfoAddressDTO();
			BeanUtils.copyProperties(personFromDb.getAddress(), address);
			responsibleInfo.setAddress(address);
		}

		return responsibleInfo;
	}

	public ResponsibleInfoDTO findByCpf(String cpf) {
		try {
			Responsible responsibleFromDb = dao.findByCpf(cpf).orElseThrow(
					() -> new ObjectNotFoundException(String.format("Responsável com cpf: [%s] não encontrado", cpf)));
			Person person = responsibleFromDb.getPerson();

			ResponsibleInfoDTO responsibleInfo = new ResponsibleInfoDTO();
			BeanUtils.copyProperties(person, responsibleInfo, "id");
			BeanUtils.copyProperties(responsibleFromDb, responsibleInfo);
			responsibleInfo.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

			if (person.getAddress() != null) {
				PersonInfoAddressDTO address = new PersonInfoAddressDTO();
				BeanUtils.copyProperties(person.getAddress(), address);
				responsibleInfo.setAddress(address);
			}

			responsibleInfo.setId(responsibleFromDb.getId());
			return responsibleInfo;
		} catch (ObjectNotFoundException e) {
			return findPersonByCpf(cpf);
		}

	}

	public ResponsibleInfoDTO update(Long id, ResponsibleInfoDTO responsible) {
		logger.info("Inciando processo de atualização de profissional ou responsável...");
		Responsible responsibleFromDb = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));

		BeanUtils.copyProperties(responsible, responsibleFromDb.getPerson(), "id");
		if (responsible.getProfessionalIdentity() != null) {
			responsibleFromDb.setProfessionalIdentity(responsible.getProfessionalIdentity().getRegisterValue());
			responsibleFromDb.setInitialsIdentity(responsible.getProfessionalIdentity().getRegisterType());
		}

		handleSpecializations(responsible, responsibleFromDb);
		dao.saveAndFlush(responsibleFromDb);
		return responsible;
	}

	private void handleSpecializations(ResponsibleInfoDTO responsible, Responsible responsibleFrom) {
		logger.info("Verificando especializações...");
		if (responsible.getSpecializations() != null) {
			logger.info("Alterações encontradas, atualizando especializações do profissional :: {}",
					responsible.getFirstName());
			responsibleFrom.setSpecializations(new ArrayList<>());
			responsible.getSpecializations().stream().map(SpecialityDTO::getId).forEach(specId -> {
				responsibleFrom.getSpecializations()
						.add(specDao.findById(specId).orElseThrow(() -> new ObjectNotFoundException(
								String.format("Especialidade com identificador %s não encontrada", specId))));
			});
		}
	}

	private void handleSpecializations(NewResponsibleDTO responsible, Responsible responsibleFrom) {
		logger.info("Verificando especializações...");
		if (responsible.getEspecialityIdsList() != null && !responsible.getEspecialityIdsList().isEmpty()) {
			logger.info("Especializações encontradas, atualizando especializações do profissional :: {}",
					responsible.getName());
			responsibleFrom.setSpecializations(new ArrayList<>());
			responsible.getEspecialityIdsList().forEach(specId -> {
				responsibleFrom.getSpecializations()
						.add(specDao.findById(specId).orElseThrow(() -> new ObjectNotFoundException(
								String.format("Especialidade com identificador %s não encontrada", specId))));

			});
		}
	}

	public void delete(Responsible entity) {
		deleteById(entity.getId());
	}

	public void deleteById(Long id) {
		Responsible responsibleFromDb = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Responsável com ID: [%s] não encontrado", id)));
		dao.delete(responsibleFromDb);

	}

	public Responsible persist(Responsible entity) {
		logger.info("Inciando processo de atualização de profissional ou responsável...");
		entity.setId(null);
		return dao.save(entity);
	}

	/**
	 * Realiza a persistência à partir de um DTO
	 * 
	 * @param responsible
	 * @return
	 */
	public ResponsibleDTO persistDTO(NewResponsibleDTO responsible) {
		Responsible domain = new Responsible();
		domain.setProfessionalIdentity(responsible.getProfessionalIdentity());
		handleSpecializations(responsible, domain);
		domain = persist(domain);

		ResponsibleDTO newResponsible = new ResponsibleDTO();
		newResponsible.setId(domain.getId());
		BeanUtils.copyProperties(domain.getPerson(), newResponsible, "id");

		return newResponsible;
	}
}
