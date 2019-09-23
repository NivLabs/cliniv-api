package br.com.ft.gdp.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ft.gdp.models.domain.EventType;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.domain.VisitEvent;
import br.com.ft.gdp.models.dto.visitEvent.VisitEventRequestDTO;
import br.com.ft.gdp.service.EventTypeService;
import br.com.ft.gdp.service.PatientService;
import br.com.ft.gdp.service.ResponsibleService;
import br.com.ft.gdp.service.VisitEventService;

/**
 * Classe VisitEventControllerTest.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class VisitEventControllerTest {
	
	private static final String OBSERVATIONS = "Realização de teste";

	private static final String TITLE = "Teste";

	private static final String URL_DOC = "C:\teste";

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private VisitEventService visitEventService;
	@MockBean
	private PatientService patientService;
	@MockBean
	private EventTypeService eventTypeService;
	@MockBean
	private ResponsibleService responsibleService;
	
	private static final String URL_BASE = "/visit-event";
	private static final Long ID_PATIENT = 1L;
	private static final Long ID_EVENT_TYPE = 1L;
	private static final Long ID_RESPONSIBLE = 1L;
	
	@Test
	@WithMockUser
	public void deveRegistrarUmVisitEvent() throws Exception {
		VisitEvent visitEvent = getVisitEvent();
		BDDMockito.given(this.patientService.findById(Mockito.anyLong())).willReturn(new Patient());
		BDDMockito.given(this.eventTypeService.findById(Mockito.anyLong())).willReturn(new EventType());
		BDDMockito.given(this.responsibleService.findById(Mockito.anyLong())).willReturn(new Responsible());
		BDDMockito.given(this.visitEventService.persist(Mockito.any(VisitEvent.class))).willReturn(visitEvent);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.urlDoc").value(URL_DOC))
				.andExpect(jsonPath("$.observations").value(OBSERVATIONS))
				.andExpect(jsonPath("$.title").value(TITLE));
		
		System.out.println(jsonPath("$.urlDoc"));
	}
	
	private VisitEvent getVisitEvent() {
		Patient patient = new Patient();
		EventType eventType = new EventType();
		Responsible responsible = new Responsible();
		
		patient.setId(ID_PATIENT);
		eventType.setId(ID_EVENT_TYPE);
		responsible.setId(ID_RESPONSIBLE);
		String urlDoc = URL_DOC;
		String title = TITLE;
		String observations = OBSERVATIONS;
		return new VisitEvent(patient , eventType, responsible, urlDoc, title, observations);
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		VisitEvent ve = new VisitEvent();
		
		Patient patient = new Patient();
		EventType eventType = new EventType();
		Responsible responsible = new Responsible();
		
		patient.setId(ID_PATIENT);
		eventType.setId(ID_EVENT_TYPE);
		responsible.setId(ID_RESPONSIBLE);
		
		ve.setId(null);
		ve.setObservations(OBSERVATIONS);
		ve.setUrlDoc(URL_DOC);
		ve.setTitle(TITLE);
		ve.setEventType(eventType);
		ve.setPatient(patient);
		ve.setResponsible(responsible);
		
		VisitEventRequestDTO dto = VisitEventRequestDTO.getDtoFrom(ve);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

}
