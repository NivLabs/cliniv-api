package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Este objeto deverá ser utilzado para armazenar HTML para template Jasper
 * 
 * @author viniciosarodrigues
 *
 */
@Schema(name = "DocumentTemplate", description = "Template de documento")
public class DocumentTemplateDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6041624350058982061L;

    @Schema(description = "Identificador único do template")
    private Long id;

    @Schema(description = "Descrição/Nome do template")
    private String description;

    public DocumentTemplateDTO() {
        super();
    }

    public DocumentTemplateDTO(Long id, String description) {
        super();
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DocumentTemplateDTO [id=");
        builder.append(id);
        builder.append(", description=");
        builder.append(description);
        builder.append("]");
        return builder.toString();
    }

}
