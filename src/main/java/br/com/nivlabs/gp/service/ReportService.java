package br.com.nivlabs.gp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import br.com.nivlabs.gp.report.JasperReportsCreator;
import br.com.nivlabs.gp.report.ReportParam;
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
    
    public ReportLayoutDTO newReporLayout(String reportName, String description, FileDTO file) {

    	ReportLayoutDTO reportLayoutDTO = new ReportLayoutDTO();
    	ReportLayout reportLayout = new ReportLayout();
    	reportLayout.setId(null);
    	reportLayout.setName(reportName);
    	reportLayout.setDescription(description);
    	reportLayout.setXml(file.getBase64());
    	reportLayout.setCreatedAt(LocalDateTime.now());
    	reportLayout.setParams(readParamsXml(file));
    	repository.save(reportLayout);
    	
    	BeanUtils.copyProperties(reportLayout, reportLayoutDTO);
    	
		return reportLayoutDTO;
    	
    }
    
    private List<ReportLayoutParameter> readParamsXml(FileDTO file) {
		
    	List<ReportLayoutParameter> parameters = new ArrayList<ReportLayoutParameter>();
    	
    	Stream<String> lines = null;
    	
    	try {
			lines = Files.lines(Paths.get(file.getUrl()));
		} catch (IOException e) {
			 logger.error("Erro ao ler xml = "+file.getUrl());
		}
    	
    	lines.parallel().forEach(line -> 
    	{
    		if (line.startsWith("<parameter")) {
    			ReportLayoutParameter param = new ReportLayoutParameter();
    			if (line.contains("name=")) {
    				int indexName = line.indexOf("name=\"");
    				param.setName(line.substring(indexName, line.indexOf("\"", indexName)));
    			}
    			
    			if (line.contains("class=")) {
    				int indexType = line.indexOf("class=\"");
    				String type = line.substring(indexType, line.indexOf("\"", indexType));
    				param.setName(convertType(type));
    			}
    			parameters.add(param);
    			
    		}
    	});
    	
		return parameters;
	}

	private String convertType(String type) {
		switch (type) {
		case "java.lang.String": {
			
			return MetaType.STRING.name();
		}
		case "java.util.Date": {
			
			return MetaType.DATE.name();
		}
		case "java.lang.Long": {
			
			return MetaType.NUMBER.name();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	public ReportLayoutDTO findReportLayoutById(Long id) {

    	ReportLayout reportLayout = findById(id);
    	ReportLayoutDTO dto = new ReportLayoutDTO();
		BeanUtils.copyProperties(reportLayout, dto);
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
    
    public DigitalDocumentDTO createDocumentFromReportLayout(Long id, ReportParam params) {
    	
    	ReportLayout reportLayout = this.findById(id);
    	
    	reportLayout.getXml();
    	File file = new File("reports/generico.xml");
    	byte[] bytes = Base64.getDecoder().decode(reportLayout.getXml());
    	try {
			FileUtils.writeByteArrayToFile( file, bytes );
		} catch (IOException e) {
			//TODO log
		}
    	
    	InputStream reportInputStream = null;
    	try {
    		reportInputStream = new ClassPathResource("reports/generico.xml").getInputStream();
		} catch (IOException e) {
			//TODO log
		}
    	
    	
		return this.createDocumentFromReport(0L,reportLayout.getName(), params,reportInputStream);
    }
    
}
