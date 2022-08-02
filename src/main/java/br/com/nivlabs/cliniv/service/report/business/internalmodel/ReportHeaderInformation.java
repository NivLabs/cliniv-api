package br.com.nivlabs.cliniv.service.report.business.internalmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe interna para criação de cabeçalho de relatórios
 * 
 * @author viniciosarodrigues
 *
 */
public class ReportHeaderInformation {

    // PARÂMETROS EXISTENTES NO CABEÇALHO DO DOCUMENTO
    private final String ATTENDANCE_ID = "ATTENDANCE_ID";
    private final String HOSPITAL_LOGO = "HOSPITAL_LOGO";
    private final String DOC_TITLE = "DOC_TITLE";
    private final String READER_NAME = "READER_NAME";
    private final String TODAY = "TODAY";
    private final String USER_ID = "USER_ID";
    private final String HOSPITAL_ADDRESS = "HOSPITAL_ADDRESS";
    private final String HOSPITAL_PHONE = "HOSPITAL_PHONE";
    private final String REPORT_TEXT = "REPORT_TEXT";
    private final String PATIENT_ID = "PATIENT_ID";
    private final String PATIENT_NAME = "PATIENT_NAME";
    private final String PATIENT_BORN_DATE = "PATIENT_BORN_DATE";
    private final String PATIENT_CNS = "PATIENT_CNS";
    private final String PATIENT_MOTHER_NAME = "PATIENT_MOTHER_NAME";
    private final String PATIENT_CPF = "PATIENT_CPF";
    private final String PATIENT_BLOOD_TYPE = "PATIENT_BLOOD_TYPE";
    private final String PATIENT_ETHINIC_GROUP = "PATIENT_ETHINIC_GROUP";
    private final String PATIENT_NATIONALITY = "PATIENT_NATIONALITY";
    private final String ATTENDANCE_INI_DATETIME = "ATTENDANCE_INI_DATETIME";
    private final String ATTENDANCE_END_DATETIME = "ATTENDANCE_END_DATETIME";
    private final String ATTENDANCE_ACCOMODATION = "ATTENDANCE_ACCOMODATION";

    private Long attendanceId;
    private String hospitalLogo;
    private String docTitle;
    private String readerName;
    private Long userId;
    private String formatedHospitalAddress;
    private String formatedHospitalPhone;
    private String reportText;
    private Long patientId;
    private String patientName;
    private LocalDate patientBornDate;
    private String patientCNS;
    private String patientMotherName;
    private String formatedPatientCPF;
    private String patientBloodType;
    private String patientEthinicGroup;
    private String patientNationality;
    private LocalDateTime attendanceIniDatetime;
    private LocalDateTime attendanceEndDatetime;
    private String attendanceAccomodation;

    private Map<String, Object> parameters = new HashMap<String, Object>();

    public ReportHeaderInformation() {
        parameters.put(TODAY, formatDatetime(LocalDateTime.now()));
        setAttendanceId(null);
        setHospitalLogo(null);
        setDocTitle(null);
        setReaderName(null);
        setUserId(null);
        setFormatedHospitalAddress(null);
        setFormatedHospitalPhone(null);
        setReportText(null);
        setPatientId(null);
        setPatientName(null);
        setPatientBornDate(null);
        setPatientCNS(null);
        setPatientMotherName(null);
        setFormatedPatientCPF(null);
        setPatientBloodType(null);
        setPatientEthinicGroup(null);
        setPatientNationality("Brasileiro");
        setAttendanceIniDatetime(null);
        setAttendanceEndDatetime(null);
        setAttendanceAccomodation(null);
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId != null ? attendanceId : 0L;
        parameters.put(ATTENDANCE_ID, this.attendanceId.toString());
    }

    public String getHospitalLogo() {
        return hospitalLogo;
    }

    public void setHospitalLogo(String hospitalLogo) {
        this.hospitalLogo = hospitalLogo;
        parameters.put(HOSPITAL_LOGO, this.hospitalLogo);
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle != null ? docTitle : "DOCUMENTO";
        parameters.put(DOC_TITLE, this.docTitle);
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName != null ? readerName : "Não informado";
        parameters.put(READER_NAME, this.readerName);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        parameters.put(USER_ID, this.userId != null ? this.userId.toString() : "Não informado");
    }

    public String getFormatedHospitalAddress() {
        return formatedHospitalAddress;
    }

    public void setFormatedHospitalAddress(String formatedHospitalAddress) {
        this.formatedHospitalAddress = formatedHospitalAddress != null ? formatedHospitalAddress : "";
        parameters.put(HOSPITAL_ADDRESS, this.formatedHospitalAddress);
    }

    public String getFormatedHospitalPhone() {
        return formatedHospitalPhone;
    }

    public void setFormatedHospitalPhone(String formatedHospitalPhone) {
        this.formatedHospitalPhone = formatedHospitalPhone != null ? formatedHospitalPhone : "";
        parameters.put(HOSPITAL_PHONE, this.formatedHospitalPhone);
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText != null ? reportText : "";
        parameters.put(REPORT_TEXT, this.reportText);
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
        parameters.put(PATIENT_ID, this.patientId != null ? this.patientId.toString() : "Não informado");
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName != null ? patientName : "Não informado";
        parameters.put(PATIENT_NAME, this.patientName);
    }

    public String getPatientCNS() {
        return patientCNS;
    }

    public void setPatientCNS(String patientCNS) {
        this.patientCNS = patientCNS != null ? patientCNS : "Não informado";
        parameters.put(PATIENT_CNS, this.patientCNS);
    }

    public String getPatientMotherName() {
        return patientMotherName;
    }

    public void setPatientMotherName(String patientMotherName) {
        this.patientMotherName = patientMotherName != null ? patientMotherName : "Não informado";
        parameters.put(PATIENT_MOTHER_NAME, this.patientMotherName);
    }

    public String getFormatedPatientCPF() {
        return formatedPatientCPF;
    }

    public void setFormatedPatientCPF(String formatedPatientCPF) {
        this.formatedPatientCPF = formatedPatientCPF != null ? formatedPatientCPF : "Não informado";
        parameters.put(PATIENT_CPF, this.formatedPatientCPF);
    }

    public String getPatientBloodType() {
        return patientBloodType;
    }

    public void setPatientBloodType(String patientBloodType) {
        this.patientBloodType = patientBloodType != null ? patientBloodType : "";
        switch (this.patientBloodType) {
            case "A_POSITIVE":
                this.patientBloodType = "A+";
                break;
            case "B_POSITIVE":
                this.patientBloodType = "B+";
                break;
            case "AB_POSITIVE":
                this.patientBloodType = "AB+";
                break;
            case "O_POSITIVE":
                this.patientBloodType = "O+";
                break;
            case "A_NEGATIVE":
                this.patientBloodType = "A-";
                break;
            case "B_NEGATIVE":
                this.patientBloodType = "B-";
                break;
            case "AB_NEGATIVE":
                this.patientBloodType = "AB-";
                break;
            case "O_NEGATIVE":
                this.patientBloodType = "O-";
                break;
            default:
                this.patientBloodType = "Não informado";
                break;
        }
        parameters.put(PATIENT_BLOOD_TYPE, this.patientBloodType);
    }

    public String getPatientEthinicGroup() {
        return patientEthinicGroup;
    }

    public void setPatientEthinicGroup(String patientEthinicGroup) {
        this.patientEthinicGroup = patientEthinicGroup != null ? patientEthinicGroup : "";
        switch (this.patientEthinicGroup) {
            case "WHITE":
                this.patientEthinicGroup = "Branco";
                break;
            case "MULTIRACIAL":
                this.patientEthinicGroup = "Pardo";
                break;
            case "BLACK":
                this.patientEthinicGroup = "Preto";
                break;
            case "INDIANS":
                this.patientEthinicGroup = "Índio";
                break;
            case "YELLOW":
                this.patientEthinicGroup = "Amarelo - Asiático";
                break;
            default:
                this.patientEthinicGroup = "Não informado";
                break;
        }
        parameters.put(PATIENT_ETHINIC_GROUP, this.patientEthinicGroup);
    }

    public String getPatientNationality() {
        return patientNationality;
    }

    public void setPatientNationality(String patientNationality) {
        this.patientNationality = patientNationality != null ? patientNationality : "Não informado";
        parameters.put(PATIENT_NATIONALITY, this.patientNationality);
    }

    public LocalDate getPatientBornDate() {
        return patientBornDate;
    }

    public void setPatientBornDate(LocalDate patientBornDate) {
        this.patientBornDate = patientBornDate;
        parameters.put(PATIENT_BORN_DATE, formatDate(this.patientBornDate));
    }

    public LocalDateTime getAttendanceIniDatetime() {
        return attendanceIniDatetime;
    }

    public void setAttendanceIniDatetime(LocalDateTime attendanceIniDatetime) {
        this.attendanceIniDatetime = attendanceIniDatetime;
        parameters.put(ATTENDANCE_INI_DATETIME, formatDatetime(this.attendanceIniDatetime));
    }

    public LocalDateTime getAttendanceEndDatetime() {
        return attendanceEndDatetime;
    }

    public void setAttendanceEndDatetime(LocalDateTime attendanceEndDatetime) {
        this.attendanceEndDatetime = attendanceEndDatetime;
        parameters.put(ATTENDANCE_END_DATETIME, formatDatetime(this.attendanceEndDatetime));
    }

    public String getAttendanceAccomodation() {
        return attendanceAccomodation;
    }

    public void setAttendanceAccomodation(String attendanceAccomodation) {
        this.attendanceAccomodation = attendanceAccomodation != null ? attendanceAccomodation : "Não informado";
        parameters.put(ATTENDANCE_ACCOMODATION, this.attendanceAccomodation);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Formata Data/Hora no padrão Brasileiro para o relatório
     * 
     * @param datetime Data/hora
     * @return Data/hora formatada
     */
    private String formatDatetime(LocalDateTime datetime) {
        if (datetime == null)
            return "Não informado";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return datetime.format(formatter);

    }

    /**
     * Formata Data no padrão Brasileiro para o relatório
     * 
     * @param date Data
     * @return Data formatada
     */
    private String formatDate(LocalDate date) {
        if (date == null)
            return "Não informado";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

}
