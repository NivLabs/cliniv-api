package br.com.nivlabs.gp.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportType {

    AttendanceStatement(1, "Declaração de Comparecimento");

    private int id;

    private String description;

    public static ReportType toEnum(Integer id) {
        if (id == null)
            return null;
        for (ReportType x : ReportType.values()) {
            if (x.id == id)
                return x;

        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
