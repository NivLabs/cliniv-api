package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDate;

import br.com.nivlabs.cliniv.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Esta classe representa as informações reduzidas do usuário, mais utilizada para listagens e paginações
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(description = "Usuário")
public class UserDTO extends PersonDTO {

    private static final long serialVersionUID = 2375401831562989624L;

    private String userName;

    public UserDTO(Long id, String fullName, String socialName, String cpf, LocalDate bornDate, String principalNumber,
            Gender gender, String userName) {
        super();
        super.setId(id);
        super.setFullName(fullName);
        super.setSocialName(socialName);
        super.setCpf(cpf);
        super.setBornDate(bornDate);
        super.setPrincipalNumber(principalNumber);
        super.setGender(gender);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        UserDTO other = (UserDTO) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO [userName=" + userName + "]";
    }

}
