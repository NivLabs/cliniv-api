package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(name = "DocumentTemplate", description = "Template de documento")
public class DocumentTemplateInfoDTO extends DataTransferObjectBase {

    @Schema(description = "Identificador único do template")
    private Long id;

    @Schema(description = "Descrição/Nome do template")
    @NotNull(message = "A descrição do modelo do documento deve ser informada")
    @NotEmpty(message = "A descrição do modelo do documento deve ser informada")
    private String description;

    @Schema(description = "Texto do template")
    @NotNull(message = "O texto do modelo do documento deve ser informado")
    @NotEmpty(message = "O texto do modelo do documento deve ser informado")
    private String text;

    public DocumentTemplateInfoDTO() {
        super();
    }

    public DocumentTemplateInfoDTO(Long id, String description, String text) {
        super();
        this.id = id;
        this.description = description;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DocumentTemplateInfoDTO [id=");
        builder.append(id);
        builder.append(", description=");
        builder.append(description);
        builder.append(", text=");
        builder.append(text);
        builder.append("]");
        return builder.toString();
    }

}
