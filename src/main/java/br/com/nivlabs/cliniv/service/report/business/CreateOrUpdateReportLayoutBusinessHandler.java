package br.com.nivlabs.cliniv.service.report.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.ReportLayout;
import br.com.nivlabs.cliniv.models.domain.ReportLayoutParameter;
import br.com.nivlabs.cliniv.models.dto.ReportLayoutParameterDTO;
import br.com.nivlabs.cliniv.report.entities.JasperReportXml;
import br.com.nivlabs.cliniv.repository.ReportParamRepository;
import br.com.nivlabs.cliniv.repository.ReportRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente abstrato para criação de operações de cadastro e atualização de Layouts de relatórios
 *
 * @author viniciosarodrigues
 * @since 10-10-2021
 *
 */
@Component
public abstract class CreateOrUpdateReportLayoutBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected ReportRepository reportRepository;
    @Autowired
    protected ReportParamRepository parameterRepository;

    /**
     * Realiza a leitura do XML para a separação dos parâmetros
     * 
     * @param file Base64 do XML Jasper
     * @param reportLayoutId Identificador único do relatório
     * @return Lista de parâmetros do XML
     * @throws IOException
     */
    protected List<ReportLayoutParameter> readParamsXml(String file, Long reportLayoutId) throws IOException {
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

    protected String convertType(String type) {
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

    /**
     * Converte uma lista de parâmetros vinda do banco para uma lista de parâmetros DTO'
     * 
     * @param params
     * @return
     */
    protected List<ReportLayoutParameterDTO> convertParams(List<ReportLayoutParameter> params) {
        List<ReportLayoutParameterDTO> convertedList = new ArrayList<>();
        params.forEach(param -> {
            ReportLayoutParameterDTO convertedParam = new ReportLayoutParameterDTO();
            convertedParam.setId(param.getId());
            convertedParam.setName(param.getName());
            convertedParam.setType(param.getType());
            convertedParam.setDescription(param.getDescription());
            convertedParam.setDefaultValue(param.getDefaultValue());
            convertedList.add(convertedParam);
        });
        return convertedList;
    }

}
