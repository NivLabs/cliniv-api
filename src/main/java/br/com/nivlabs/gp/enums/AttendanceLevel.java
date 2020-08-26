package br.com.nivlabs.gp.enums;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Enumerador que define o tipo de grau de risco de um atendimento
 * 
 * @author viniciosarodrigues
 *
 */
public enum AttendanceLevel {

    LOW("Atendimento com risco baixo"),
    MEDIUM("Atendimento com risco médio"),
    HIGH("atendimento com risco alto");

    private String description;

    private AttendanceLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public AttendanceLevel toEnum(String level) {
        if (StringUtils.isNullOrEmpty(level))
            return null;
        else
            for (AttendanceLevel type : AttendanceLevel.values())
                if (type.description.equalsIgnoreCase(level))
                    return type;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Nível de risco de atendimento: ".concat(level));
    }
}
