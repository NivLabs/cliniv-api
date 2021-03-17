package br.com.nivlabs.gp.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.nivlabs.gp.enums.DigitalDocumentType;
import br.com.nivlabs.gp.enums.MetaType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.ReportLayout;
import br.com.nivlabs.gp.models.domain.ReportLayoutParameter;
import br.com.nivlabs.gp.models.dto.DigitalDocumentDTO;
import br.com.nivlabs.gp.models.dto.FileDTO;
import br.com.nivlabs.gp.models.dto.ReportGenerationRequestDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutDTO;
import br.com.nivlabs.gp.models.dto.ReportLayoutParameterDTO;
import br.com.nivlabs.gp.report.JasperReportsCreator;
import br.com.nivlabs.gp.report.ReportParam;
import br.com.nivlabs.gp.report.entities.JasperReportXml;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JasperReportsCreator report;

    @Autowired
    private ReportRepository repository;

    @Autowired
    private ReportParamRepository paramRepository;

    /**
     * Cria um documento digital anexado a um atendimento
     * 
     * @param attendanceEventId
     * @param reportName
     * @param params
     * @param reportInputStream
     * @return
     */
    public DigitalDocumentDTO createDocumentFromReport(Long attendanceEventId, String reportName, ReportParam params,
                                                       InputStream reportInputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            logger.info("Iniciando a criação do documento à partir dos parâmetros :: Verificando template do documento :: {} :: Instância -> {}",
                        reportName, reportInputStream);

            JasperPrint jasperPrint = report.create(params, reportInputStream);

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
        } catch (IOException e1) {
            logger.error("Falha ao tentar gerar o relatório! Motivo :: {}", e1.getMessage(), e1);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e1.getMessage());
        }

    }

    /**
     * Cria um novo Layout
     * 
     * @param id
     * @param file
     * @return
     */
    @Transactional
    public ReportLayoutDTO newReporLayout(Long id, FileDTO file) {
        logger.info("Iniciando processo de criação de layout de relatório...");
        ReportLayoutDTO reportLayoutDTO = new ReportLayoutDTO();
        ReportLayout reportLayout = new ReportLayout();
        reportLayout.setId(id);
        reportLayout.setName(file.getName());
        reportLayout.setDescription(file.getName());
        reportLayout.setXml(file.getBase64());
        reportLayout.setCreatedAt(LocalDateTime.now());
        try {
            reportLayout.setParams(readParamsXml(file.getBase64(), id));
            logger.info("Persistindo layout na base de dados...");
            repository.saveAndFlush(reportLayout);
            BeanUtils.copyProperties(reportLayout, reportLayoutDTO);
            reportLayoutDTO.setBase64(reportLayout.getXml());
            logger.info("Layout criado, iniciando persistência de parâmetros...");

            List<ReportLayoutParameter> params = readParamsXml(file.getBase64(), reportLayout.getId());
            paramRepository.saveAll(params);
            paramRepository.flush();
            reportLayoutDTO.setParams(convertParams(params));
            logger.info("Parâmetros salvos, devolvendo resposta para o canal de requisição.");
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha na criação do relatório. Entre em contato com o suporte!");
        }

        return reportLayoutDTO;

    }

    /**
     * Realiza a leitura do XML para a separação dos parâmetros
     * 
     * @param file Base64 do XML Jasper
     * @param reportLayoutId Identificador único do relatório
     * @return Lista de parâmetros do XML
     * @throws IOException
     */
    private List<ReportLayoutParameter> readParamsXml(String file, Long reportLayoutId) throws IOException {
        logger.info("Preparando parâmetros do XML...");
        List<ReportLayoutParameter> parameters = new ArrayList<>();

        byte[] bytes = Base64.getDecoder().decode(file);

        try (InputStream reportInputStream = new ByteArrayInputStream(bytes)) {
            XmlMapper mapper = new XmlMapper();
            JasperReportXml reportObject = mapper.readValue(reportInputStream, JasperReportXml.class);

            logger.info("Iniciando parse dos parâmetros...");
            reportObject.getParameters().forEach(parameter -> {
                logger.info("Primeiro parâmetro encontrado :: {} | {} ", parameter.getName(), parameter.getType());

                ReportLayoutParameter param = new ReportLayoutParameter(
                        null,
                        parameter.getName(),
                        convertType(parameter.getType()),
                        parameter.getParameterDescription(),
                        parameter.getDefaultValueExpression(),
                        new ReportLayout(reportLayoutId));

                logger.info("Adicionando parâmetro à lista...");
                parameters.add(param);

            });
        }

        logger.info("Leitura finalizada. Total de parâmetros manipulados :: {}", parameters.size());
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
                throw new HttpException(HttpStatus.BAD_REQUEST,
                        "Tipo do parâmetro não mapeado pelo servidor, entrar em contato com o suporte :: Tipo - ".concat(type));
        }
    }

    public ReportLayoutDTO findReportLayoutById(Long id) {
        ReportLayout reportLayout = findById(id);
        ReportLayoutDTO dto = new ReportLayoutDTO();
        BeanUtils.copyProperties(reportLayout, dto);
        dto.setBase64(reportLayout.getXml());
        List<ReportLayoutParameterDTO> listParam = new ArrayList<>();
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
                String.format("Layout com o identificador %s não encontrado!", id)));
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

    /**
     * Cria relatório à partir de um layout pré-configurado
     * 
     * @param id Identificador único de Layout de Relatório
     * @param params Parâmetros do Layout
     * @return Documento digital gerado do relatório
     */
    public DigitalDocumentDTO createDocumentFromReportLayout(Long id, ReportGenerationRequestDTO params) {

        ReportLayout reportLayout = findById(id);

        byte[] bytes = Base64.getDecoder().decode(reportLayout.getXml());

        try (InputStream reportInputStream = new ByteArrayInputStream(bytes)) {
            ReportParam reportParam = convertParams(params);
            return createDocumentFromReport(0L, reportLayout.getName(), reportParam, reportInputStream);
        } catch (IOException e) {
            logger.error("Falha ao gerar relatório", e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao gerar documento...\n".concat(e.getMessage()));
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
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    try {
                        reportParam.getParams().put(parameter.getName(), formato.parse(parameter.getValue()));
                    } catch (ParseException e) {
                        throw new HttpException(HttpStatus.BAD_REQUEST,
                                "Formato de data inválido, formato esperado :: yyyy-MM-dd'T'HH:mm:ss.SSS");
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

    public void deleteLayoutById(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public ReportLayoutDTO update(Long id, FileDTO file) {
        logger.info("Buscando layout do relatório...");
        ReportLayout layout = findById(id);
        logger.info("Layout encontrado :: {}", layout.getName());
        logger.info("Iniciando remoção dos antigos parâmetros...");
        paramRepository.deleteByLayout(new ReportLayout(id));
        paramRepository.flush();
        logger.info("Remoção concluída com sucesso!");
        logger.info("Iniciando criação dos novos parâmetros...");
        try {
            layout.setName(file.getName());
            layout.setParams(readParamsXml(file.getBase64(), id));
            paramRepository.saveAll(layout.getParams());
            repository.saveAndFlush(layout);
            ReportLayoutDTO response = new ReportLayoutDTO();
            response.setId(id);
            response.setBase64(file.getBase64());
            response.setCreatedAt(layout.getCreatedAt());
            response.setName(layout.getName());
            response.setParams(convertParams(layout.getParams()));
            return response;
        } catch (IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao atualizar o relatório... Favor entrar em contato com o suporte.");
        }
    }

    /**
     * Converte uma lista de parâmetros vinda do banco para uma lista de parâmetros DTO'
     * 
     * @param params
     * @return
     */
    private List<ReportLayoutParameterDTO> convertParams(List<ReportLayoutParameter> params) {
        List<ReportLayoutParameterDTO> convertedList = new ArrayList<>();
        params.forEach(param -> {
            ReportLayoutParameterDTO convertedParam = new ReportLayoutParameterDTO();
            BeanUtils.copyProperties(param, convertedParam);
            convertedList.add(convertedParam);
        });
        return convertedList;
    }

}