package br.com.nivlabs.gp.report;

import java.util.List;

import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.models.dto.QuestionDTO;

/**
 * Classe que representa os parâmetros de um relatório dinâmico
 * 
 * @author viniciosarodrigues
 *
 */
public class DynamicFormParams extends ReportParam {
    private static final long serialVersionUID = -7425109036420315805L;

    private Long attendanceId;

    private String patientName;

    private String patientGender;

    private List<AllergyDTO> allergies;

    private List<QuestionDTO> responses;

    public DynamicFormParams(Long attendanceId, String patientName, String patientGender, List<AllergyDTO> allergies,
            List<QuestionDTO> responses) {
        super();
        this.attendanceId = attendanceId;
        this.patientName = patientName;
        this.patientGender = patientGender;
        this.allergies = allergies;
        this.responses = responses;
    }

    public DynamicFormParams() {
        super();
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public List<AllergyDTO> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<AllergyDTO> allergies) {
        this.allergies = allergies;
    }

    public List<QuestionDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<QuestionDTO> responses) {
        this.responses = responses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
        result = prime * result + ((attendanceId == null) ? 0 : attendanceId.hashCode());
        result = prime * result + ((patientGender == null) ? 0 : patientGender.hashCode());
        result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
        result = prime * result + ((responses == null) ? 0 : responses.hashCode());
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
        DynamicFormParams other = (DynamicFormParams) obj;
        if (allergies == null) {
            if (other.allergies != null)
                return false;
        } else if (!allergies.equals(other.allergies))
            return false;
        if (attendanceId == null) {
            if (other.attendanceId != null)
                return false;
        } else if (!attendanceId.equals(other.attendanceId))
            return false;
        if (patientGender == null) {
            if (other.patientGender != null)
                return false;
        } else if (!patientGender.equals(other.patientGender))
            return false;
        if (patientName == null) {
            if (other.patientName != null)
                return false;
        } else if (!patientName.equals(other.patientName))
            return false;
        if (responses == null) {
            if (other.responses != null)
                return false;
        } else if (!responses.equals(other.responses))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AnamnesisParams [attendanceId=" + attendanceId + ", patientName=" + patientName + ", patientGender=" + patientGender
                + ", allergies=" + allergies + ", responses=" + responses + "]";
    }

}
