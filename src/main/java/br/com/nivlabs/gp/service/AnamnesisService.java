/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.nivlabs.gp.models.domain.Attendance;
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
public class AnamnesisService implements GenericService {

    private static final String FALSE = "false";
    private static final String TRUE = "true";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnamneseRepository dao;

    @Autowired
    private AttendanceService attendanceService;

    public Page<AnamnesisDTO> searchDTOPage(Pageable pageSettings) {
        Page<Anamnesis> page = dao.findAll(pageSettings);
        List<AnamnesisDTO> newPage = new ArrayList<>();
        page.getContent().forEach(domain -> newPage.add(domain.getAnamneseDTOFromDomain()));

        return new PageImpl<>(newPage, pageSettings, page.getTotalElements());
    }

    public List<Anamnesis> findByAttendance(Attendance attendance) {
        return dao.findByAttendance(attendance);
    }

    public Anamnesis findById(Long id) {
        try {
            return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Anamnesis Item ID: [%s] não encontrado!", id)));

        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Anamnesis update(Long id, Anamnesis entity) {
        Anamnesis anamnese = findById(id);
        BeanUtils.copyProperties(entity, anamnese, "id");
        return anamnese;
    }

    public void deleteById(Long id) {
        Anamnesis anamnese = findById(id);
        dao.delete(anamnese);
    }

    /**
     * Deleta um questionário respondido de anamnese baseado no identificador do atendimento
     * 
     * @param attendanceId
     */
    public void deleteAnamnesisFromAttendance(Long attendanceId) {
        logger.info("Solicitação de exclução de anamnese recebida, código do atendimento :: {}", attendanceId);
        List<Long> listOfAnamesesis = findByAttendance(new Attendance(attendanceId)).stream().map(Anamnesis::getId)
                .collect(Collectors.toList());
        if (listOfAnamesesis.isEmpty())
            logger.info("Não há anamnese para exclusão");
        else
            logger.info("Foram encontradas um total de {} questões respondidas, inicializando exclusão dos itens...",
                        listOfAnamesesis.size());
        listOfAnamesesis.forEach(id -> dao.deleteById(id));
        logger.info("Processo finalizado com sucesso");

    }

    /**
     * Cria um novo questionário respondido de anamnese
     * 
     * @param request
     * @return
     */
    public NewAnamnesisDTO newAnamnesisResponse(NewAnamnesisDTO request) {
        logger.info("Iniciando o preenchimento de um novo questionário de anamnese...");
        attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());
        if (!findByAttendance(new Attendance(request.getAttendanceId())).isEmpty()) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Este atendimento já possui um relatório de anamnese, não é possível responder novamente");
        }
        request.getListOfResponse().forEach(item -> {
            validateQuestions(item);
            item.setAttendanceId(request.getAttendanceId());
            persist(item.getAnamnesesDomainFromDTO());
        });
        return request;
    }

    /**
     * Valita as questões
     * 
     * @param anamnese
     */
    private void validateQuestions(AnamnesisDTO anamnese) {
        logger.info("Iniciando validação do questionário...");
        if (anamnese.getAnamnesisItem() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Seu questionário está com questão nula");
        else if (StringUtils.isNullOrEmpty(anamnese.getAnamnesisItem().getQuestion())
                || anamnese.getAnamnesisItem().getMetaType() == null)
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Seu questionário não está nulo mas está incompleto. Informe a questão e o tipo da questão");
        else if (StringUtils.isNullOrEmpty(anamnese.getResponse()))
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Você possui questão sem resposta, revise seu questionário");
        checkMetaTypes(anamnese);
    }

    /**
     * Valida os tipos das respostas
     * 
     * @param anamnese
     */
    private void checkMetaTypes(AnamnesisDTO anamnese) {
        logger.info("Verificando meta tipos das respostas");
        switch (anamnese.getAnamnesisItem().getMetaType()) {
            case number:
                if (!StringUtils.isNumeric(anamnese.getResponse()))
                    throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O valor da resposta deve ser numérica");
                break;
            case bool:
                if (StringUtils.isNullOrEmpty(anamnese.getResponse())
                        || (!anamnese.getResponse().equalsIgnoreCase(TRUE)
                                && !anamnese.getResponse().equalsIgnoreCase(FALSE)))
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

    public Anamnesis persist(Anamnesis entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
