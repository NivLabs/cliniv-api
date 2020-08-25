package br.com.nivlabs.gp.controller.filters;

import br.com.nivlabs.gp.enums.Modality;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

/**
 * Filtro customizado para busca paginada de operadoras de saúde
 * 
 * @author viniciosarodrigues
 *
 */
public class HealthOperatorFilters extends CustomFilters {

    private static final long serialVersionUID = -7472397996695208164L;

    private String id;

    private String ansCode;

    private String cnpj;

    private String companyName;

    private String fantasyName;

    private Modality modality;

    public String getCompanyName() {
        return "%".concat(this.companyName).concat("%");
    }

    public String getFantasyName() {
        return "%".concat(this.fantasyName).concat("%");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnsCode() {
        return ansCode;
    }

    public void setAnsCode(String ansCode) {
        this.ansCode = ansCode;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((ansCode == null) ? 0 : ansCode.hashCode());
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result + ((fantasyName == null) ? 0 : fantasyName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modality == null) ? 0 : modality.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        HealthOperatorFilters other = (HealthOperatorFilters) obj;
        if (ansCode == null) {
            if (other.ansCode != null)
                return false;
        } else if (!ansCode.equals(other.ansCode))
            return false;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
            return false;
        if (fantasyName == null) {
            if (other.fantasyName != null)
                return false;
        } else if (!fantasyName.equals(other.fantasyName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modality != other.modality)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HealthOperatorFilters [id=" + id + ", ansCode=" + ansCode + ", cnpj=" + cnpj + ", companyName=" + companyName
                + ", fantasyName=" + fantasyName + ", modality=" + modality + "]";
    }

}
