package br.com.nivlabs.cliniv.enums;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.util.StringUtils;
import org.springframework.http.HttpStatus;

/**
 * Tipos sanguíneos conhecidos
 *
 * @author viniciosarodrigues
 */
public enum BloodType {

    A_POSITIVE("A_POSITIVE", "A+"),
    B_POSITIVE("B_POSITIVE", "B+"),
    AB_POSITIVE("AB_POSITIVE", "AB+"),
    O_POSITIVE("O_POSITIVE", "O+"),
    A_NEGATIVE("A_NEGATIVE", "A-"),
    B_NEGATIVE("B_NEGATIVE", "B-"),
    AB_NEGATIVE("AB_NEGATIVE", "AB-"),
    O_NEGATIVE("O_NEGATIVE", "O-");
    final private String name;
    final private String description;

    private BloodType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BloodType toEnum(String bloodType) {
        if (StringUtils.isNullOrEmpty(bloodType))
            return null;
        else
            for (BloodType type : BloodType.values())
                if (type.name.equalsIgnoreCase(bloodType))
                    return type;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Tipo sanguíneo não encontrada: ".concat(bloodType));
    }
}
