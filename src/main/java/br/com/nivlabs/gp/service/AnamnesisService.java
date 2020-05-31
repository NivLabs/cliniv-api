/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Anamnesis;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import br.com.nivlabs.gp.models.dto.NewAnamnesisDTO;
import br.com.nivlabs.gp.repository.AnamneseRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Camada de serviço de anamenese do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class AnamnesisService implements GenericService<Anamnesis, Long> {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AnamneseRepository dao;

	@Autowired
	private AttendanceService attendanceService;

	public Page<AnamnesisDTO> searchDTOPage(Pageable pageSettings) {
		Page<Anamnesis> page = dao.findAll(pageSettings);
		List<AnamnesisDTO> newPage = new ArrayList<>();
		page.getContent().forEach(domain -> {
			newPage.add(domain.getAnamneseDTOFromDomain());
		});

		return new PageImpl<>(newPage, pageSettings, page.getTotalElements());
	}

	@Override
	public Anamnesis findById(Long id) {
		try {
			return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
					String.format("Anamnesis Item ID: [%s] não encontrado!", id)));

		} catch (HttpException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Anamnesis update(Long id, Anamnesis entity) {
		Anamnesis anamnese = findById(id);
		BeanUtils.copyProperties(entity, anamnese, "id");
		return anamnese;
	}

	@Override
	public void delete(Anamnesis entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteById(Long id) {
		Anamnesis anamnese = findById(id);
		dao.delete(anamnese);
	}

	public NewAnamnesisDTO newAnamnesisResponse(NewAnamnesisDTO request) {
		logger.info("Iniciando o preenchimento de um novo questionário de anamnese...");
		attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());
		request.getListOfResponse().forEach(item -> {
			validateQuestions(item);
			item.setAttendanceId(request.getAttendanceId());
			persist(item.getAnamnesesDomainFromDTO());
		});
		return request;
	}

	private void validateQuestions(AnamnesisDTO item) {
		logger.info("Iniciando validação do questionário...");
		if (item.getAnamnesisItem() == null)
			throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Seu questionário está com questão nula");
		else if (StringUtils.isNullOrEmpty(item.getAnamnesisItem().getQuestion())
				|| item.getAnamnesisItem().getMetaType() == null)
			throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Seu questionário não está nulo mas está incompleto. Informe a questão e o tipo da questão");
		else if (StringUtils.isNullOrEmpty(item.getResponse()))
			throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Você possui questão sem resposta, revise seu questionário");
		checkMetaTypes(item);
	}

	private void checkMetaTypes(AnamnesisDTO item) {
		logger.info("Verificando meta tipos das respostas");
		switch (item.getAnamnesisItem().getMetaType()) {
		case number:
			if (!StringUtils.isNumeric(item.getResponse()))
				throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor da resposta deve ser numérica");
			break;
		case bool:
			if (StringUtils.isNullOrEmpty(item.getResponse()) || (!item.getResponse().toLowerCase().equals("true")
					&& !item.getResponse().toLowerCase().equals("false")))
				throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
						"O valor da resposta só pode ser true ou false");
			break;
		case string:
			break;
		default:
			throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Este metatipo é inválido para uma questão. Metatipos válidos -> number, bool ou string");
		}
	}

	@Override
	public Anamnesis persist(Anamnesis entity) {
		entity.setId(null);
		return dao.save(entity);
	}

}
