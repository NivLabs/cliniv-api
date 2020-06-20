package br.com.nivlabs.gp.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ReportParam implements Serializable {

    private static final long serialVersionUID = -6101578697271991627L;

    private String hospitalLogo;

    private String hospitalName;

    private Map<String, Object> params = new HashMap<>();

    public Map<String, Object> getParams() {
        params.put("hospitalLogo", this.getHospitalLogo());
        params.put("hospitalName", this.getHospitalName());

        return params;
    }

}
