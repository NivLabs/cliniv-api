package br.com.nivlabs.cliniv.service.patient.business;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.models.dto.DocumentDTO;
import br.com.nivlabs.cliniv.models.dto.PatientAppointmentsReportRequestDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.report.ReportParam;
import br.com.nivlabs.cliniv.repository.AppointmentRepository;
import br.com.nivlabs.cliniv.repository.PatientRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.InstituteService;
import br.com.nivlabs.cliniv.service.report.ReportService;
import br.com.nivlabs.cliniv.service.report.business.GenerateReportBusinessHandler;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
public class GenerateUpcomingAppointmentsBusinessHandler implements BaseBusinessHandler {

    private final Logger logger;
    private final AppointmentRepository repo;
    private final ReportService reportService;
    private final InstituteService instituteService;
    private final PatientRepository patientRepository;
    private final UserService userService;
    private final ResponsibleService responsibleService;
    private static final String REPORT_SOURCE = "reports/agendamentos_paciente.jrxml";

    @Autowired
    public GenerateUpcomingAppointmentsBusinessHandler(
            final AppointmentRepository repo,
            final Logger logger,
            final ReportService reportService,
            final InstituteService instituteService,
            final PatientRepository patientRepository,
            final UserService userService,
            final ResponsibleService responsibleService) {
        this.repo = repo;
        this.logger = logger;
        this.reportService = reportService;
        this.instituteService = instituteService;
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.responsibleService = responsibleService;
    }

    @Transactional
    public DigitalDocumentDTO execute(Long patientId, PatientAppointmentsReportRequestDTO request) {
        final var initDate = Optional.ofNullable(request.getInitDate()).orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A data inicial não pode ser nula")).atTime(LocalTime.MIN);
        final var endDate = Optional.ofNullable(request.getEndDate()).orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "A data final não pode ser nula")).atTime(LocalTime.MAX);
        logger.info("Iniciando processo de geração de relatório de agendamentos de paciente :: {} - de {} até {}", patientId, initDate, endDate);
        final var appointments = repo.findAppointmentsByPatientIdAndRangeDate(patientId, initDate, endDate);


        logger.info("Buscando informações da instituição :: Logo em base 64 + Nome da instituição...");
        ReportParam params = new ReportParam();
        final var instituteDTO = instituteService.getSettings();
        final var patientEntity = patientRepository.findById(patientId).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format("Paciente com o identificador %s não encontrado.", patientId)));

        final var instituteAddress = instituteDTO.getCustomerInfo().getAddress();
        params.getParams().put(GenerateReportBusinessHandler.HOSPITAL_LOGO, instituteDTO.getCustomerInfo().getLogoBase64());
        if (instituteAddress != null) {
            logger.info("Iniciando parâmetros com dados da instituição...");
            params.getParams().put("HOSPITAL_ADDRESS", instituteAddress.getStreet() + ", " + instituteAddress.getAddressNumber() + ", "
                    + (instituteAddress.getComplement() != null ? instituteAddress.getComplement() + ", "
                    : "") + instituteAddress.getCity() + " - " + instituteAddress.getState() + " - " + StringUtils.printCEP(instituteAddress.getPostalCode()));
        }
        params.getParams().put("HOSPITAL_PHONE", instituteDTO.getCustomerInfo().getPhone());
        params.getParams().put("TODAY", LocalDateTime.now().atZone(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        logger.info("Iniciando parâmetros com dados do paciente...");
        params.getParams().put("PATIENT_ID", patientId.toString());
        params.getParams().put("PATIENT_NAME", patientEntity.getPerson().getFullName());
        params.getParams().put("PATIENT_BORN_DATE", patientEntity.getPerson().getBornDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        params.getParams().put("PATIENT_CNS", patientEntity.getCnsNumber() != null ? patientEntity.getCnsNumber() : "Não informado");
        params.getParams().put("PATIENT_MOTHER_NAME", patientEntity.getPerson().getMotherName() != null ? patientEntity.getPerson().getMotherName() : "Não informado");
        params.getParams().put("PATIENT_CPF", patientEntity.getPerson().getCpf() != null ? patientEntity.getPerson().getCpf() : "Não informado");
        params.getParams().put("PATIENT_BLOOD_TYPE", patientEntity.getPerson().getBloodType() != null ? patientEntity.getPerson().getBloodType().getDescription() : "Não informado");
        params.getParams().put("PATIENT_ETHINIC_GROUP", patientEntity.getPerson().getEthnicGroup() != null ? patientEntity.getPerson().getEthnicGroup().getDescription() : "Não informado");
        params.getParams().put("PATIENT_NATIONALITY", patientEntity.getPerson().getNationality() != null ? patientEntity.getPerson().getNationality() : "Não informado");

        final StringBuilder reportText = new StringBuilder();
        appointments.forEach(appointmentDTO -> reportText.append(appointmentDTO.handlerApointmentInPatientReportReport()));

        final var responsible = getResponsible();
        params.getParams().put("RESPONSIBLE_REGISTER", responsible.getProfessionalIdentity() != null ? responsible.getProfessionalIdentity().getRegisterType() + " " + responsible.getProfessionalIdentity().getRegisterValue() : "Não possui");
        params.getParams().put("REPORT_TEXT", reportText.toString());
        params.getParams().put("READER_NAME", responsible.getFullName());

        params.getParams().forEach((s, o) ->
                logger.info("Chave: {} | Valor: {}", s, o));


        try {
            return reportService.generateDocumentFromJxmlStream(new ClassPathResource(REPORT_SOURCE).getInputStream(),
                    "Relatório de Atendimento", false, params);
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar relatório.", e);
        }
    }

    private ResponsibleInfoDTO getResponsible() {
        final DocumentDTO document = userService.findByUserName(SecurityContextUtil.getAuthenticatedUser().getUsername()).getDocument();
        if (document == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O cadastro do seu usuário está sem CPF informado, complete o cadastro para continuar com a geração de documento.");
        }
        return responsibleService.findByCpf(document.getValue());
    }

}
