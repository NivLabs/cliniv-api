package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Prescrição do paciente")
public class PrescriptionInfoDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 6743345972601222205L;

    @Schema(description = "Identificador único da prescrição")
    Long id;

    @NotNull(message = "O identificador do atendimento deve ser informado")
    @Schema(description = "Identificador único do atendimento")
    private Long attendanceId;

    @NotNull(message = "A data/hora de início de vigência da prescrição deve ser informada")
    @Schema(description = "Data/Hora início")
    private LocalDateTime datetimeInit;

    @NotNull(message = "A data/hora de fim de vigência da prescrição deve ser informada")
    @Schema(description = "Data/Hora fim")
    private LocalDateTime datetimeEnd;

    @Schema(description = "Itens da prescrição")
    List<PrescriptionItemDTO> items = new ArrayList<>();

    @Schema(description = "Indica uma prescrição especial com duas vias para medicamentos de receita obrigatória")
    private boolean special;

    @Schema(description = "Indica que a prescrição deve ser usada no prontuário para controle de checagem de medicamento")
    private boolean insertToMedicalRecords;

    public PrescriptionInfoDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDateTime getDatetimeInit() {
        return datetimeInit;
    }

    public void setDatetimeInit(LocalDateTime datetimeInit) {
        this.datetimeInit = datetimeInit;
    }

    public LocalDateTime getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(LocalDateTime datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

    public List<PrescriptionItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItemDTO> items) {
        this.items = items;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public boolean isInsertToMedicalRecords() {
        return insertToMedicalRecords;
    }

    public void setInsertToMedicalRecords(boolean insertToMedicalRecords) {
        this.insertToMedicalRecords = insertToMedicalRecords;
    }

    @Override
    public String toString() {
        return "PrescriptionInfoDTO ["
                + "id=" + id
                + ", attendanceId=" + attendanceId
                + ", datetimeInit=" + datetimeInit
                + ", datetimeEnd=" + datetimeEnd
                + ", items=" + items
                + ", special=" + special
                + ", insertToMedicalRecords=" + insertToMedicalRecords
                + "]";
    }

}