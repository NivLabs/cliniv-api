package br.com.nivlabs.cliniv.report.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Objeto mapeado dos parâmetros do XML Jasper
 * 
 * @author viniciosarodrigues
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JasperParameterXml implements Serializable {

    private static final long serialVersionUID = 2767273233597008277L;

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;
    @JacksonXmlProperty(isAttribute = true, localName = "class")
    private String type;

    private String parameterDescription;

    private String defaultValueExpression;

    public JasperParameterXml() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValueExpression() {
        return defaultValueExpression;
    }

    public void setDefaultValueExpression(String defaultValueExpression) {
        this.defaultValueExpression = defaultValueExpression;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    @Override
    public String toString() {
        return "JasperParameter [parameterDescription=" + parameterDescription + ", defaultValueExpression=" + defaultValueExpression + "]";
    }

}
