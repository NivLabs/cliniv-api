package br.com.nivlabs.gp.report;

import java.util.List;

import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnamnesisParams extends ReportParam {
	private static final long serialVersionUID = -7425109036420315805L;

	private Long attendanceId;

	private Long patientId;

	private String patientName;

	private String patientGender;

	private List<AllergyDTO> allergies;

	private List<AnamnesisDTO> responses;
}
