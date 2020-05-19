package br.com.nivlabs.gp.models.dto.visitEvent;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.nivlabs.gp.models.domain.EventType;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Responsible;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe VisitEventResponseDTO.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitEventResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Patient patient;
    private EventType eventType;
    private Responsible responsible;
    private String urlDoc;
    private String title;
    private String observations;
    private LocalDateTime eventDateTime;
}
