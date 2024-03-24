package br.com.nivlabs.cliniv.report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReportParam implements Serializable {

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
