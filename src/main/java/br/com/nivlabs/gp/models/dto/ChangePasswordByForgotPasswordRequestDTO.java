package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Schema(description = "Requisição de Senha perdida")
public class ChangePasswordByForgotPasswordRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8663867905476131957L;

    @Schema(description = "Nome de usuário ou e-mail")
    @NotNull(message = "Informe um e-mail ou nome de usuário!")
    private String username;

    @Schema(description = "Nome completo da mãe")
    @NotNull(message = "Informe o nome materno completo!")
    private String motherName;

    @Schema(description = "Data de nascimento")
    @NotNull(message = "Informe a data de nascimento!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate bornDate;

    @Schema(description = "Nova senha")
    @NotNull(message = "Informe a nova senha!")
    private String newPassword;

    public ChangePasswordByForgotPasswordRequestDTO() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bornDate == null) ? 0 : bornDate.hashCode());
        result = prime * result + ((motherName == null) ? 0 : motherName.hashCode());
        result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        ChangePasswordByForgotPasswordRequestDTO other = (ChangePasswordByForgotPasswordRequestDTO) obj;
        if (bornDate == null) {
            if (other.bornDate != null)
                return false;
        } else if (!bornDate.equals(other.bornDate))
            return false;
        if (motherName == null) {
            if (other.motherName != null)
                return false;
        } else if (!motherName.equals(other.motherName))
            return false;
        if (newPassword == null) {
            if (other.newPassword != null)
                return false;
        } else if (!newPassword.equals(other.newPassword))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChangePasswordByForgotPasswordRequestDTO [username=" + username + ", motherName=" + motherName + ", bornDate=" + bornDate + ", newPassword="
                + newPassword + "]";
    }

}
