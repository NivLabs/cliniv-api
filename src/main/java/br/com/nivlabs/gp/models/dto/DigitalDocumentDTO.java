package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;

import br.com.nivlabs.gp.models.domain.DigitalDocumentType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("Digital Document")
public class DigitalDocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -870502165996369141L;

    private Long id;

    private DigitalDocumentType type;

    private String base64;

    private String name;

    private LocalDateTime createdAt;

}
