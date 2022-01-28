package br.com.nivlabs.gp.models.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;


public class NewDynamicFormAnsweredDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1489473679769549274L;

    @Schema(description = "Título do documento")
    @NotNull(message = "O título do documento deve ser informado")
    private String documentTitle;

    @Schema(description = "Respostas do formulário")
    private Set<QuestionDTO> listOfResponse = new HashSet<>();

    public NewDynamicFormAnsweredDTO() {
        super();
    }

    public NewDynamicFormAnsweredDTO(@NotNull(message = "O título do documento deve ser informado") String documentTitle,
            Set<QuestionDTO> listOfResponse) {
        super();
        this.documentTitle = documentTitle;
        this.listOfResponse = listOfResponse;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public Set<QuestionDTO> getListOfResponse() {
        return listOfResponse;
    }

    public void setListOfResponse(Set<QuestionDTO> listOfResponse) {
        this.listOfResponse = listOfResponse;
    }

    @Override
    public String toString() {
        return "NewDynamicFormAnsweredDTO [documentTitle=" + documentTitle + ", listOfResponse=" + listOfResponse + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((documentTitle == null) ? 0 : documentTitle.hashCode());
        result = prime * result + ((listOfResponse == null) ? 0 : listOfResponse.hashCode());
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
        NewDynamicFormAnsweredDTO other = (NewDynamicFormAnsweredDTO) obj;
        if (documentTitle == null) {
            if (other.documentTitle != null)
                return false;
        } else if (!documentTitle.equals(other.documentTitle))
            return false;
        if (listOfResponse == null) {
            if (other.listOfResponse != null)
                return false;
        } else if (!listOfResponse.equals(other.listOfResponse))
            return false;
        return true;
    }

}
