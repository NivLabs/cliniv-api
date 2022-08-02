package br.com.nivlabs.cliniv.manager.models;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

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

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "DH_CONFIRMACAO")
    private LocalDateTime confirmationDateTime;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getConfirmationDateTime() {
        return confirmationDateTime;
    }

    public void setConfirmationDateTime(LocalDateTime confirmationDateTime) {
        this.confirmationDateTime = confirmationDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cgc, cgcType, clinicName, confirmationDateTime, email, id, managerName, principalPhone, uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        return Objects.equals(cgc, other.cgc) && cgcType == other.cgcType && Objects.equals(clinicName, other.clinicName)
                && Objects.equals(confirmationDateTime, other.confirmationDateTime) && Objects.equals(email, other.email)
                && Objects.equals(id, other.id) && Objects.equals(managerName, other.managerName)
                && Objects.equals(principalPhone, other.principalPhone) && Objects.equals(uuid, other.uuid);
    }

}
