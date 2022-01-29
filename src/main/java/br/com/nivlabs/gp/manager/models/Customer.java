package br.com.nivlabs.gp.manager.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;

/**
 * Tabela de cadastro de clientes da NivLabs
 * 
 * @author viniciosarodrigues
 *
 */
@Entity
@Table(name = "NIV_PRE_CLIENTE")
public class Customer extends BaseObjectWithId {

    private static final long serialVersionUID = 7525247457493766900L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME_CLINICA")
    private String clinicName;

    @Column(name = "NOME_GESTOR")
    private String managerName;

    @Column(name = "TIPO_CGC")
    @Enumerated(EnumType.STRING)
    private CGCType cgcType;

    @Column(name = "CGC")
    private String cgc;

    @Column(name = "TELEFONE_PRINCIPAL")
    private String principalPhone;

    @Column(name = "EMAIL")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public CGCType getCgcType() {
        return cgcType;
    }

    public void setCgcType(CGCType cgcType) {
        this.cgcType = cgcType;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [id=");
        builder.append(id);
        builder.append(", clinicName=");
        builder.append(clinicName);
        builder.append(", managerName=");
        builder.append(managerName);
        builder.append(", cgcType=");
        builder.append(cgcType);
        builder.append(", cgc=");
        builder.append(cgc);
        builder.append(", principalPhone=");
        builder.append(principalPhone);
        builder.append(", email=");
        builder.append(email);
        builder.append("]");
        return builder.toString();
    }

}
