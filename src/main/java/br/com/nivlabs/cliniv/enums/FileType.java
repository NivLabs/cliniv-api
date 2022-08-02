package br.com.nivlabs.cliniv.enums;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Enum específico para representação de content-type de arquivos
 * 
 * @author viniciosarodrigues
 *
 */
public enum FileType {

    PDF("application/pdf"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    XML("application/xml");

    private String description;

    private FileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public FileType toEnum(String mimeType) {
        if (StringUtils.isNullOrEmpty(mimeType))
            return null;
        else
            for (FileType type : FileType.values())
                if (type.description.equalsIgnoreCase(mimeType))
                    return type;
        throw new HttpException(HttpStatus.BAD_REQUEST, "Content-Type do arquivo não mapeado: ".concat(mimeType));
    }
}
