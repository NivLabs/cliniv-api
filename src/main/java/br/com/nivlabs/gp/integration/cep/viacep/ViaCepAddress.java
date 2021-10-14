package br.com.nivlabs.gp.integration.cep.viacep;

import br.com.nivlabs.gp.integration.RestClientObject;

/**
 * Objeto de endereço da Via Cep
 * 
 * @author viniciosarodrigues
 *
 */
public class ViaCepAddress extends RestClientObject {

    private static final long serialVersionUID = 3089097894140675162L;

    private String cep;
    private String logradouro;
    private String complement;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

    public ViaCepAddress() {
        super();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
        result = prime * result + ((cep == null) ? 0 : cep.hashCode());
        result = prime * result + ((complement == null) ? 0 : complement.hashCode());
        result = prime * result + ((gia == null) ? 0 : gia.hashCode());
        result = prime * result + ((ibge == null) ? 0 : ibge.hashCode());
        result = prime * result + ((localidade == null) ? 0 : localidade.hashCode());
        result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
        result = prime * result + ((uf == null) ? 0 : uf.hashCode());
        result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ViaCepAddress other = (ViaCepAddress) obj;
        if (bairro == null) {
            if (other.bairro != null)
                return false;
        } else if (!bairro.equals(other.bairro))
            return false;
        if (cep == null) {
            if (other.cep != null)
                return false;
        } else if (!cep.equals(other.cep))
            return false;
        if (complement == null) {
            if (other.complement != null)
                return false;
        } else if (!complement.equals(other.complement))
            return false;
        if (gia == null) {
            if (other.gia != null)
                return false;
        } else if (!gia.equals(other.gia))
            return false;
        if (ibge == null) {
            if (other.ibge != null)
                return false;
        } else if (!ibge.equals(other.ibge))
            return false;
        if (localidade == null) {
            if (other.localidade != null)
                return false;
        } else if (!localidade.equals(other.localidade))
            return false;
        if (logradouro == null) {
            if (other.logradouro != null)
                return false;
        } else if (!logradouro.equals(other.logradouro))
            return false;
        if (uf == null) {
            if (other.uf != null)
                return false;
        } else if (!uf.equals(other.uf))
            return false;
        if (unidade == null) {
            if (other.unidade != null)
                return false;
        } else if (!unidade.equals(other.unidade))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ViaCepAddress [cep=" + cep + ", logradouro=" + logradouro + ", complement=" + complement + ", bairro=" + bairro
                + ", localidade=" + localidade + ", uf=" + uf + ", unidade=" + unidade + ", ibge=" + ibge + ", gia=" + gia + "]";
    }

}
