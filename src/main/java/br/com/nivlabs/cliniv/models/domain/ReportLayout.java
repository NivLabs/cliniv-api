package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObject;

/**
 * Classe ReportLayout.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@Entity
@Table(name = "LAYOUT_RELATORIO")
public class ReportLayout extends BaseObject {

    private static final long serialVersionUID = 545660687640626225L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "DATA")
    private LocalDateTime createdAt;

    @Column(name = "XML")
    private String xml;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "layout", fetch = FetchType.EAGER)
    private List<ReportLayoutParameter> params = new ArrayList<>();

    public ReportLayout() {
        super();
    }

    public ReportLayout(Long id, String name, String description, LocalDateTime createdAt, String xml, List<ReportLayoutParameter> params) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.xml = xml;
        this.params = params;
    }

    public ReportLayout(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public List<ReportLayoutParameter> getParams() {
        return params;
    }

    public void setParams(List<ReportLayoutParameter> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ReportLayout [id=" + id + ", name=" + name + ", description=" + description + ", createdAt=" + createdAt + ", xml=" + xml
                + ", params=" + params + "]";
    }

}
