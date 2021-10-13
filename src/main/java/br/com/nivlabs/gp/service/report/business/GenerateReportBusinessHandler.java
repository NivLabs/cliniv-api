package br.com.nivlabs.gp.service.report.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.gp.report.JasperReportsCreator;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.ReportRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import net.sf.jasperreports.engine.JasperExportManager;
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
    protected JasperReportsCreator jasperReportCreator;
    @Autowired
    protected ReportRepository reportRepository;

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

        try (InputStream reportInputStream = new ByteArrayInputStream(bytes)) {
            ReportParam reportParam = convertParams(params);
            return generateFromJxmlStream(0L, reportLayout.getName(), reportParam, reportInputStream);
        } catch (Exception e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento...\n".concat(e.getMessage()), e);
        }
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
