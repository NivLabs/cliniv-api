package br.com.nivlabs.gp.report;

import java.util.Date;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.gp.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Parâmetros necessários para montar a declaração de comparecimento do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceParams extends ReportParam {

	private static final long serialVersionUID = 5738508744853919737L;

	private String patientName;

	private String documentType;

	private String documentValue;

	private String responsibleName;

	private String responsibleDocumentType;

	private String responsibleDocumentValue;

	@DateTimeFormat(iso = ISO.DATE)
	private Date startHour;

	@DateTimeFormat(iso = ISO.DATE)
	private Date endHour;

	@Override
	public Map<String, Object> getParams() {
		super.getParams().put("patientName", this.getPatientName());
		super.getParams().put("documentType", this.getDocumentType());
		super.getParams().put("documentValue", this.getDocumentValue());

		super.getParams().put("responsibleName", this.getResponsibleName());
		super.getParams().put("responsibleDocumentType", this.getResponsibleDocumentType());
		super.getParams().put("responsibleDocumentValue", this.getResponsibleDocumentValue());

		super.getParams().put("startHour", StringUtils.dateFormat(this.getStartHour(), StringUtils.BRASIL_DATE_TIME));
		super.getParams().put("endHour", StringUtils.dateFormat(this.getEndHour(), StringUtils.BRASIL_DATE_TIME));

		return super.getParams();
	}

}
