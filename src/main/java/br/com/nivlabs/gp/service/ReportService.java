package br.com.nivlabs.gp.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.domain.ReportLayoutParameter;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutParameterDTO;
import br.com.nivlabs.gp.models.dto.ReportParameterDTO;
import br.com.nivlabs.gp.report.JasperReportsCreator;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.repository.ReportParamRepository;
import br.com.nivlabs.gp.repository.ReportRepository;
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

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportParamRepository paramRepository;

    public DigitalDocumentDTO createDocumentFromReport(Long attendanceEventId, String reportName, ReportParam params,
                                                       InputStream reportInputStream) {
        try {
            logger.info("Iniciando a criação do documento à partir dos parâmetros :: Verificando template do documento :: {} :: Instância -> {}",
                        reportName, reportInputStream);

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

    public ReportLayoutDTO newReporLayout(Long id, FileDTO file) {

        ReportLayoutDTO reportLayoutDTO = new ReportLayoutDTO();
        ReportLayout reportLayout = new ReportLayout();
        reportLayout.setId(id);
        reportLayout.setName(file.getName());
        reportLayout.setDescription(file.getName());
        reportLayout.setXml(file.getBase64());
        reportLayout.setCreatedAt(LocalDateTime.now());
        try {
            reportLayout.setParams(readParamsXml(file.getBase64(), reportLayout));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.saveAndFlush(reportLayout);

        BeanUtils.copyProperties(reportLayout, reportLayoutDTO);

        return reportLayoutDTO;

    }

    private List<ReportLayoutParameter> readParamsXml(String file, ReportLayout reportLayout) throws IOException {

        List<ReportLayoutParameter> parameters = new ArrayList<ReportLayoutParameter>();

        byte[] bytes = Base64.getDecoder().decode(file);

        InputStream reportInputStream = new ByteArrayInputStream(bytes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(reportInputStream));

        while (reader.ready()) {
            String line = reader.readLine();
            if (line.contains("<parameter ")) {
                ReportLayoutParameter param = new ReportLayoutParameter();
                param.setLayout(reportLayout);
                if (line.contains("name=")) {
                    int indexName = line.indexOf("name=\"");
                    param.setName(line.substring(indexName + 6, line.indexOf("\"", indexName + 6)));
                }

                if (line.contains("class=")) {
                    int indexType = line.indexOf("class=\"");
                    String type = line.substring(indexType + 7, line.indexOf("\"", indexType + 7));
                    param.setType(convertType(type));
                }
                parameters.add(param);

            }
        }

        return parameters;
    }

    private String convertType(String type) {
        switch (type) {
            case "java.lang.String":
                return MetaType.STRING.name();
            case "java.util.Date":
                return MetaType.DATE.name();
            case "java.lang.Long":
                return MetaType.NUMBER.name();
            default:
                throw new IllegalArgumentException("Unexpected value: " + type);
        }
    }

    public ReportLayoutDTO findReportLayoutById(Long id) {

        ReportLayout reportLayout = findById(id);
        ReportLayoutDTO dto = new ReportLayoutDTO();
        BeanUtils.copyProperties(reportLayout, dto);
        List<ReportLayoutParameterDTO> listParam = new ArrayList<ReportLayoutParameterDTO>();
        if (reportLayout.getParams() != null) {
            for (ReportLayoutParameter param : reportLayout.getParams()) {
                ReportLayoutParameterDTO paramDto = new ReportLayoutParameterDTO();
                BeanUtils.copyProperties(param, paramDto);
                listParam.add(paramDto);
            }
            dto.setParams(listParam);
        }
        return dto;
    }

    private ReportLayout findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Layout ID: [%s] não encontrado!", id)));
    }

    public Page<ReportLayoutDTO> findPageOfReportLayout(Pageable pageSettings) {
        Page<ReportLayout> page = repository.findAll(pageSettings);
        List<ReportLayoutDTO> newPage = new ArrayList<>();
        page.getContent().forEach(domain -> {
            ReportLayoutDTO reportLayoutDTO = new ReportLayoutDTO();
            BeanUtils.copyProperties(domain, reportLayoutDTO);
            newPage.add(reportLayoutDTO);
        });
        return new PageImpl<>(newPage, pageSettings, page.getTotalElements());
    }

    public DigitalDocumentDTO createDocumentFromReportLayout(Long id, ReportParameterDTO params) {

        ReportLayout reportLayout = this.findById(id);

        byte[] bytes = Base64.getDecoder().decode(reportLayout.getXml());

        InputStream reportInputStream = new ByteArrayInputStream(bytes);

        ReportParam reportParam = validateParams(reportLayout, params);

        return this.createDocumentFromReport(0L, reportLayout.getName(), reportParam, reportInputStream);
    }

    private ReportParam validateParams(ReportLayout reportLayout, ReportParameterDTO params) {

        ReportParam reportParam = new ReportParam();
        Map<String, Object> map = new HashMap<>();

        params.getParams().forEach((k, v) -> {
            reportLayout.getParams().forEach(param -> {

                if (param.getName().equals(k)) {

                    Object obj = new Object();
                    try {
                        switch (MetaType.valueOf(param.getType())) {
                            case STRING:
                                obj = String.valueOf(v);
                                break;
                            case DATE:
                                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                obj = formato.parse(v);
                                break;
                            case NUMBER:
                                obj = Long.valueOf(v);
                                break;
                            case BOOL:
                                obj = Boolean.valueOf(v);
                                break;
                            default:
                                throw new IllegalArgumentException("Unexpected value: " + k + ", " + v);
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Unexpected value: " + k + ", " + v);
                    }
                    map.put(k, obj);
                }
            });
        });
        reportParam.setParams(map);

        return reportParam;
    }

    public void deleteLayoutById(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }

    public ReportLayoutDTO update(Long id, FileDTO file) {
        ReportLayout layout = findById(id);
        paramRepository.deleteByLayout(layout);
        return newReporLayout(id, file);
    }

}
