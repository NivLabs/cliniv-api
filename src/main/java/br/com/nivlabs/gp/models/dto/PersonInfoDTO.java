package br.com.nivlabs.gp.models.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.GenderIdentity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe PersonInfoDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 9 de fev de 2020
 */
@ApiModel("Informações detalhadas da pessoa")
public abstract class PersonInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 4735834196671409605L;

    @ApiModelProperty("Identificadfor único")
    private Long id;

    @ApiModelProperty("Nome completo da pessoa")
    @NotNull(message = "O nome completo é obrigatório")
    @Size(min = 3, max = 45, message = "O nome completo é obrigatório")
    private String fullName;

    @ApiModelProperty("Nome social da pessoa")
    private String socialName;

    @ApiModelProperty("Data de nascimento da pessoa")
    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    @ApiModelProperty("Documento da pessoa")
    @NotNull(message = "O documento deve ser informado")
    private DocumentDTO document;

    @ApiModelProperty("Sexo")
    @NotNull(message = "O gênero deve ser informado")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty("Identidade de gênero")
    @Enumerated(EnumType.STRING)
    private GenderIdentity genderIdentity;

    @ApiModelProperty("Nome do pai")
    private String fatherName;

    @ApiModelProperty("Nome da mãe")
    private String motherName;

    @ApiModelProperty("Número de telefone|celular principal")
    private String principalNumber;

    @ApiModelProperty("Número de telefone|celular segundário")
    private String secondaryNumber;

    @ApiModelProperty("Endereço")
    private AddressDTO address;

    @ApiModelProperty("Email")
    private String email;

    @ApiModelProperty("Foto do perfil")
    private String profilePhoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public DocumentDTO getDocument() {
		return document;
	}

	public void setDocument(DocumentDTO document) {
		this.document = document;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public GenderIdentity getGenderIdentity() {
		return genderIdentity;
	}

	public void setGenderIdentity(GenderIdentity genderIdentity) {
		this.genderIdentity = genderIdentity;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getPrincipalNumber() {
		return principalNumber;
	}

	public void setPrincipalNumber(String principalNumber) {
		this.principalNumber = principalNumber;
	}

	public String getSecondaryNumber() {
		return secondaryNumber;
	}

	public void setSecondaryNumber(String secondaryNumber) {
		this.secondaryNumber = secondaryNumber;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
		result = prime * result + ((document == null) ? 0 : document.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((genderIdentity == null) ? 0 : genderIdentity.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
		result = prime * result + ((principalNumber == null) ? 0 : principalNumber.hashCode());
		result = prime * result + ((profilePhoto == null) ? 0 : profilePhoto.hashCode());
		result = prime * result + ((secondaryNumber == null) ? 0 : secondaryNumber.hashCode());
		result = prime * result + ((socialName == null) ? 0 : socialName.hashCode());
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
		PersonInfoDTO other = (PersonInfoDTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bornDate == null) {
			if (other.bornDate != null)
				return false;
		} else if (!bornDate.equals(other.bornDate))
			return false;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.equals(other.document))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (gender != other.gender)
			return false;
		if (genderIdentity != other.genderIdentity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motherName == null) {
			if (other.motherName != null)
				return false;
		} else if (!motherName.equals(other.motherName))
			return false;
		if (principalNumber == null) {
			if (other.principalNumber != null)
				return false;
		} else if (!principalNumber.equals(other.principalNumber))
			return false;
		if (profilePhoto == null) {
			if (other.profilePhoto != null)
				return false;
		} else if (!profilePhoto.equals(other.profilePhoto))
			return false;
		if (secondaryNumber == null) {
			if (other.secondaryNumber != null)
				return false;
		} else if (!secondaryNumber.equals(other.secondaryNumber))
			return false;
		if (socialName == null) {
			if (other.socialName != null)
				return false;
		} else if (!socialName.equals(other.socialName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonInfoDTO [id=" + id + ", fullName=" + fullName + ", socialName=" + socialName + ", bornDate="
				+ bornDate + ", document=" + document + ", gender=" + gender + ", genderIdentity=" + genderIdentity
				+ ", fatherName=" + fatherName + ", motherName=" + motherName + ", principalNumber=" + principalNumber
				+ ", secondaryNumber=" + secondaryNumber + ", address=" + address + ", email=" + email
				+ ", profilePhoto=" + profilePhoto + "]";
	}
    
}
