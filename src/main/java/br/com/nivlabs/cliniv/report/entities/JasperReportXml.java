package br.com.nivlabs.cliniv.report.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Objeto mapeado do XML Jasper
 * 
 * @author viniciosarodrigues
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JasperReportXml implements Serializable {

    private static final long serialVersionUID = 3811672384675396684L;

    @JacksonXmlProperty(localName = "parameter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<JasperParameterXml> parameters = new ArrayList<>();

    public JasperReportXml() {
        super();
    }

    public JasperReportXml(List<JasperParameterXml> parameters) {
        super();
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "JasperReportXml [parameters=" + parameters + "]";
    }

    public List<JasperParameterXml> getParameters() {
        return parameters;
    }

    public void setParameters(List<JasperParameterXml> parameters) {
        this.parameters = parameters;
    }

}
