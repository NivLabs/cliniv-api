package br.com.nivlabs.cliniv.enums;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.util.StringUtils;
import org.springframework.http.HttpStatus;

public enum EthnicGroup {
    WHITE("WHITE", "Branco"),
    MULTIRACIAL("MULTIRACIAL", "Pardo"),
    BLACK("BLACK", "Preto"),
    INDIANS("INDIANS", "Índio"),
    YELLOW("YELLOW", "Amarelo - Asiático");

    final private String name;
    final private String description;

    private EthnicGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public EthnicGroup toEnum(String ethnicGroup) {
        if (StringUtils.isNullOrEmpty(ethnicGroup))
            return null;
        else
            for (EthnicGroup group : EthnicGroup.values())
                if (group.name.equalsIgnoreCase(ethnicGroup))
                    return group;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Grupo étnico não encontrada: ".concat(ethnicGroup));
    }
}
