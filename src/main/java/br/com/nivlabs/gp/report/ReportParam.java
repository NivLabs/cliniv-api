package br.com.nivlabs.gp.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportParam implements Serializable {

    private static final long serialVersionUID = -6101578697271991627L;

    private Map<String, Object> params = new HashMap<>();

    public ReportParam() {
        super();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
