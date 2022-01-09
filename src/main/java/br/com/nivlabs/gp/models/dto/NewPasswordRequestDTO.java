package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe NewPasswordRequestDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 27 de jan de 2020
 */

@Schema(description = "Requisição de nova senha")
public class NewPasswordRequestDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -173297306237874136L;

    @NotNull(message = "Informe a senha antiga")
    private String oldPassword;

    @NotNull(message = "Informe a nova senha")
    private String newPassword;

    @NotNull(message = "Informe novamente a nova senha")
    private String confirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((confirmNewPassword == null) ? 0 : confirmNewPassword.hashCode());
        result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
        result = prime * result + ((oldPassword == null) ? 0 : oldPassword.hashCode());
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
        NewPasswordRequestDTO other = (NewPasswordRequestDTO) obj;
        if (confirmNewPassword == null) {
            if (other.confirmNewPassword != null)
                return false;
        } else if (!confirmNewPassword.equals(other.confirmNewPassword))
            return false;
        if (newPassword == null) {
            if (other.newPassword != null)
                return false;
        } else if (!newPassword.equals(other.newPassword))
            return false;
        if (oldPassword == null) {
            if (other.oldPassword != null)
                return false;
        } else if (!oldPassword.equals(other.oldPassword))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NewPasswordRequestDTO [oldPassword=" + oldPassword + ", newPassword=" + newPassword
                + ", confirmNewPassword=" + confirmNewPassword + "]";
    }

}
