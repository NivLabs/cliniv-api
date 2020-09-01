package br.com.nivlabs.gp.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.report.JasperReportsCreator;
import br.com.nivlabs.gp.report.ReportParam;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * Classe responsável por armazenar os documentos gerados por relatórios em base de dados
 * 
 * @author Vinícios Rodrigues (viniciosarodrigues@gmail.com)
 * @since 21 de jun de 2020
 *
 *
 */
@Service
public class ReportService implements GenericService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JasperReportsCreator report;

    public DigitalDocumentDTO createDocumentFromReport(Long attendanceEventId, String reportName, ReportParam params,
                                                       InputStream reportInputStream) {
        try {
            logger.info("Iniciando a criação do documento à partir dos parâmetros :: Verificando template do documento :: Instância -> {}",
                        reportInputStream);
            JasperPrint jasperPrint = report.create(params, reportInputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
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

        } catch (JRException e) {
            logger.error("Falha ao tentar gerar o relatório! Motivo :: {}", e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
