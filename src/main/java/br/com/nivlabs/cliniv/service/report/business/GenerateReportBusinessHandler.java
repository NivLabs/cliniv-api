package br.com.nivlabs.cliniv.service.report.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nivlabs.cliniv.enums.DigitalDocumentType;
import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Attendance;
import br.com.nivlabs.cliniv.models.domain.AttendanceEvent;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.ReportLayout;
import br.com.nivlabs.cliniv.models.dto.AddressDTO;
import br.com.nivlabs.cliniv.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.cliniv.models.dto.InstituteDTO;
import br.com.nivlabs.cliniv.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.cliniv.report.JasperReportsCreator;
import br.com.nivlabs.cliniv.report.ReportParam;
import br.com.nivlabs.cliniv.repository.AttendanceEventRepository;
import br.com.nivlabs.cliniv.repository.AttendanceRepository;
import br.com.nivlabs.cliniv.repository.ReportRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.InstituteService;
import br.com.nivlabs.cliniv.service.report.business.internalmodel.ReportHeaderInformation;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import br.com.nivlabs.cliniv.util.StringUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * Componente específico para geração de relatórios
 *
 * @author viniciosarodrigues
 * @since 09-10-2021
 *
 */
@Component
public class GenerateReportBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    private JasperReportsCreator jasperReportCreator;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private InstituteService instituteService;

    private static final String GENERIC_REPORT_SOURCE = "reports/generico.jrxml";

    /**
     * Cria um documento digital anexado a um atendimento
     * 
     * @param attendanceEventId Identificador único do atendimento (se houver)
     * @param reportName Nome do relatório
     * @param params Parâmetros do relatório
     * @param reportInputStream Stream do Relatório (jxml)
     * @return Documento digital do relatório gerado
     */
    @Transactional
    public DigitalDocumentDTO generateFromJxmlStream(Long attendanceEventId, String reportName, ReportParam params,
                                                     InputStream reportInputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            logger.info("Iniciando a criação do documento à partir dos parâmetros :: Verificando template do documento :: {} :: Instância -> {}",
                        reportName, reportInputStream);

            JasperPrint jasperPrint = jasperReportCreator.create(params, reportInputStream);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            logger.info("Documento criado com sucesso!");

            logger.info("Preparando documento para a inclusão no banco de dados...");
            DigitalDocumentDTO document = new DigitalDocumentDTO();
            document.setCreatedAt(LocalDateTime.now());
            document.setName(reportName);
            document.setType(DigitalDocumentType.PDF);
            document.setBase64(Base64.getEncoder().withoutPadding().encodeToString(outputStream.toByteArray()));
            document.setAttendanceEventId(attendanceEventId);

            return document;
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Cria relatório à partir de um layout pré-configurado
     * 
     * @param layoutId Identificador único de Layout de Relatório
     * @param params Parâmetros do Layout
     * @return Documento digital gerado do relatório
     */
    @Transactional
    public DigitalDocumentDTO generateFromLayout(Long layoutId, ReportGenerationRequestDTO params) {

        ReportLayout reportLayout = reportRepository.findById(layoutId).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Layout com o identificador %s não encontrado!", layoutId)));

        byte[] bytes = Base64.getDecoder().decode(reportLayout.getXml());
        return generate(bytes, reportLayout.getName(), params);
    }

    /**
     * Gera um relatório à partir de um array de bytes
     * 
     * @param bytes Array de bytes do relatório
     * @param reportName Nome do relatório
     * @param params Parâmetros do relatório
     * @return Documento digital com o base64 do relatório
     */
    @Transactional
    public DigitalDocumentDTO generate(byte[] bytes, String reportName, ReportGenerationRequestDTO params) {
        try (InputStream reportInputStream = new ByteArrayInputStream(bytes)) {
            ReportParam reportParam = convertParams(params);
            return generateFromJxmlStream(0L, reportName, reportParam, reportInputStream);
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento...\n".concat(e.getMessage()), e);
        }
    }

    /**
     * Gera um relatório à partir de um array de bytes
     * 
     * @param bytes Array de bytes do relatório
     * @param reportName Nome do relatório
     * @param params Parâmetros do relatório
     * @return Documento digital com o base64 do relatório
     */
    @Transactional
    public DigitalDocumentDTO generate(InputStream reportInputStream, String reportName, ReportParam reportParams) {
        try {
            return generateFromJxmlStream(0L, reportName, reportParams, reportInputStream);
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento...\n".concat(e.getMessage()), e);
        } finally {
            try {
                reportInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gera um relatório à partir de um texto livre
     * 
     * @param text Texto livre
     * @return Documento digital
     */
    @Transactional
    public DigitalDocumentDTO generateFromFormatedText(Long attendanceEventId, String title, String text) {
        InputStream reportInputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            reportInputStream = new ClassPathResource(GENERIC_REPORT_SOURCE)
                    .getInputStream();
            outputStream = new ByteArrayOutputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(reportInputStream),
                                                                   getReportHeaderInformationFromAttendance(attendanceEventId, title, text)
                                                                           .getParameters(),
                                                                   new JREmptyDataSource());

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            DigitalDocumentDTO document = new DigitalDocumentDTO();
            document.setCreatedAt(LocalDateTime.now());
            document.setName(title);
            document.setType(DigitalDocumentType.PDF);
            document.setBase64(Base64.getEncoder().withoutPadding().encodeToString(outputStream.toByteArray()));
            document.setAttendanceEventId(attendanceEventId);
            return document;
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento...\n".concat(e.getMessage()), e);
        } finally {
            try {
                if (reportInputStream != null)
                    reportInputStream.close();
            } catch (Exception e) {
                logger.error("Falha ao fechar o InputStream da geração do relatório!", e);
            }
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception e) {
                logger.error("Falha ao fechar o OutputStream da geração do relatório!", e);
            }
        }
    }

    /**
     * Busca informações do cabeçalho do relatório à partir de um código de atendimento
     * 
     * @param attendanceId Código do atendimento
     * @return Informações do cabeçalho do relatório
     */
    @Transactional
    private ReportHeaderInformation getReportHeaderInformationFromAttendance(Long attendanceEventId, String title, String text) {
        ReportHeaderInformation headerInfo = new ReportHeaderInformation();
        headerInfo.setDocTitle(title);
        headerInfo.setReportText(text);
        AttendanceEvent event = attendanceEventRepo.findById(attendanceEventId).orElseThrow(() -> new HttpException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Evento de atendimento não encontrado para anexar o documento, entre em contato com o suporte se o erro persistir."));

        // Parâmetros da instituição
        InstituteDTO institute = instituteService.getSettings();
        headerInfo.setHospitalLogo(institute.getCustomerInfo().getLogoBase64());
        headerInfo.setFormatedHospitalPhone(StringUtils.printPhone(institute.getCustomerInfo().getPhone()));
        AddressDTO address = institute.getCustomerInfo().getAddress();
        headerInfo.setFormatedHospitalAddress(address.getStreet() + ", " + address.getAddressNumber() + ", "
                + (address.getComplement() != null ? address.getComplement() + ", "
                                                   : "")
                + address.getCity() + " - "
                + address.getState() + " - " + StringUtils.printCEP(address.getPostalCode()));

        // Parâmetros do atendimento
        Attendance attendance = event.getAttendance();
        if (attendance.getPatient() == null) {
            attendance = attendanceRepo.findById(attendance.getId())
                    .orElseThrow(() -> new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                            "Não foi possível encontrar o atendimento para o evento de atendimento em questão, favor solicitar suporte."));
        }
        headerInfo.setAttendanceId(attendance.getId());
        headerInfo.setAttendanceAccomodation(attendance.getCurrentAccommodation() != null
                                                                                          ? attendance.getCurrentAccommodation()
                                                                                                  .getDescription()
                                                                                          : null);
        headerInfo.setAttendanceIniDatetime(attendance.getEntryDateTime());
        headerInfo.setAttendanceEndDatetime(attendance.getExitDateTime());

        // Parâmetros do solicitante
        headerInfo.setReaderName(SecurityContextUtil.getAuthenticatedUser().getPersonName());
        // headerInfo.setUserId(SecurityContextUtil.getAuthenticatedUser().getUsername());

        // Parâmetros do paciente
        Patient patient = attendance.getPatient();
        Person person = patient.getPerson();
        headerInfo.setPatientId(patient.getId());
        headerInfo.setPatientBloodType(person.getBloodType() != null ? person.getBloodType().name() : null);
        headerInfo.setPatientBornDate(person.getBornDate());
        headerInfo.setPatientName(person.getFullName());
        headerInfo.setPatientCNS(patient.getCnsNumber());
        headerInfo.setPatientEthinicGroup(person.getEthnicGroup() != null ? person.getEthnicGroup().name() : null);
        headerInfo.setPatientMotherName(person.getMotherName());
        headerInfo.setPatientNationality(person.getNationality());
        headerInfo.setFormatedPatientCPF(person.getCpf());

        return headerInfo;
    }

    /**
     * Converte os parâmetros da requisição em parâmetros do relatório
     * 
     * @param request Requisição de geração de relatório
     * @return Parâmetros de relatório
     */
    private ReportParam convertParams(ReportGenerationRequestDTO request) {

        ReportParam reportParam = new ReportParam();

        request.getParams().forEach(parameter -> {
            switch (MetaType.valueOf(parameter.getType())) {
                case STRING:
                    reportParam.getParams().put(parameter.getName(), String.valueOf(parameter.getValue()));
                    break;
                case DATE:
                    try {
                        String date = parameter.getValue();
                        if (date.length() == 16) {
                            date = date.concat(":00.000Z");
                        } else if (date.length() == 19) {
                            date = date.concat(".000Z");
                        }
                        reportParam.getParams().put(parameter.getName(), Instant.parse(date));
                    } catch (Exception e) {
                        throw new HttpException(HttpStatus.BAD_REQUEST,
                                "Formato de data inválido, formato esperado :: yyyy-MM-ddTHH:mm:ss.SSS");
                    }
                    break;
                case NUMBER:
                    reportParam.getParams().put(parameter.getName(), Long.valueOf(parameter.getValue()));
                    break;
                case BOOL:
                    reportParam.getParams().put(parameter.getName(), parameter.getValue());
                    break;
                default:
                    throw new HttpException(HttpStatus.BAD_REQUEST,
                            String.format("Valor não esperado pela aplicação %s, %s", parameter.getName(), parameter.getValue()));
            }

        });

        return reportParam;
    }
}
