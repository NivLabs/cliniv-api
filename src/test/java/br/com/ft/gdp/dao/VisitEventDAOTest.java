package br.com.ft.gdp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ft.gdp.models.domain.EventType;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.domain.VisitEvent;

/**
 * Classe VisitEventDAOTest.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 08 Sept, 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VisitEventDAOTest {

	@Autowired
	VisitEventDao visitEventDAO;
	@Autowired
	EventTypeDao eventDAO;
	@Autowired
	ResponsibleDao responsibleDao;
	
	Responsible responsible;
	EventType eventType;
	Patient patient;
	VisitEvent visitEvent;

	@Before
	public void setUp() {

		patient  = new Patient();
		
		eventType = new EventType();
		eventType.setDescription("Tipo Teste");
		eventType.setName("Teste");
		this.eventDAO.save(eventType);

		responsible = new Responsible();
		responsible.setName("Williams Gomes");
		responsible.setProfessionalIdentity("Médico Chefe");
		this.responsibleDao.save(responsible);
		
		String urlDoc = "D:\\test\\test\\test.pdf";
		String title = "Visita do Dia 10-9-2019";
		String observations = "Paciente pode receber visitar as 23:00";

		visitEvent = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
	}

	@Test
	public void deveSalvarVisitEvent() {

		String urlDoc = "D:\\test\\test\\test.pdf";
		String title = "Visita do Dia 10-9-2019";
		String observations = "Paciente pode receber visitar as 23:00";

		visitEvent = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);

		this.visitEventDAO.save(visitEvent);
		
		List<VisitEvent> findAll = this.visitEventDAO.findAll();
		
		assertEquals(1, findAll.size());
	}
	
	@Test
	public void deveAlterarORegistroVariosOuApenasUmAtributo() {
		Optional<VisitEvent> ve = this.visitEventDAO.findById(1l);
		ve.ifPresent(event -> {
			event.setTitle("Titulo alterado");
			
			this.visitEventDAO.save(event);
			
			Optional<VisitEvent> optional = this.visitEventDAO.findById(1l);
			VisitEvent visitEventFinal = optional.get();
			assertNotEquals("Visita do Dia 10-9-2019", visitEventFinal.getTitle());
			assertEquals("Titulo alterado", visitEventFinal.getTitle());
		});
		
		ve.ifPresent(event -> {
			event.setTitle("Titulo alterado 2");
			event.setObservations("OBS Alterada");
			
			this.visitEventDAO.save(event);
			
			Optional<VisitEvent> optional = this.visitEventDAO.findById(1l);
			VisitEvent visitEventFinal = optional.get();
			assertNotEquals("Visita do Dia 10-9-2019", visitEventFinal.getTitle());
			assertEquals("Titulo alterado", visitEventFinal.getTitle());
			
			assertNotEquals("Paciente pode receber visitar as 23:00", visitEventFinal.getTitle());
			assertEquals("OBS Alterada", visitEventFinal.getTitle());
		});
	}
	
	@Test
	public void deveListarTodosOsEventosVisita() {
		String urlDoc = "D:\\test\\test\\test.pdf";
		String title = "Visita do Dia 10-9-2019";
		String observations = "Paciente pode receber visitar as 23:00";
		
		this.visitEventDAO.deleteAll();
		VisitEvent evt;
		VisitEvent evt2;
		VisitEvent evt3;
		VisitEvent evt4;

		evt = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt2 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt3 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt4 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		
		this.visitEventDAO.save(evt);
		this.visitEventDAO.save(evt2);
		this.visitEventDAO.save(evt3);
		this.visitEventDAO.save(evt4);
		
		List<VisitEvent> ves = this.visitEventDAO.findAll();
		
		assertEquals(4, ves.size());
		
	}
	
	@Test
	public void deveListarTodosOsEventosVisitaPaginado() {
		String urlDoc = "D:\\test\\test\\test.pdf";
		String title = "Visita do Dia 10-9-2019";
		String observations = "Paciente pode receber visitar as 23:00";
		
		this.visitEventDAO.deleteAll();
		VisitEvent evt;
		VisitEvent evt2;
		VisitEvent evt3;
		VisitEvent evt4;

		evt = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt2 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt3 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		evt4 = new VisitEvent(patient, eventType, responsible, urlDoc, title, observations);
		
		this.visitEventDAO.save(evt);
		this.visitEventDAO.save(evt2);
		this.visitEventDAO.save(evt3);
		this.visitEventDAO.save(evt4);
		
		Pageable pageable = PageRequest.of(0, 3);
		
		Page<VisitEvent> page = this.visitEventDAO.findAll(pageable);
		
		assertEquals(0, page.getPageable().getPageNumber());
		assertEquals(1, page.nextPageable().getPageNumber());
		assertEquals(4, page.getTotalElements());
		assertEquals(2, page.getTotalPages());
		
		System.out.println(page.getTotalElements());
		System.out.println(page.nextPageable());
		
		List<VisitEvent> visits = page.getContent();
		assertEquals(3, visits.size());
		
	}
 
	@After
	public void tearDown() throws Exception {
		this.visitEventDAO.deleteAll();
		this.eventDAO.deleteAll();
		this.responsibleDao.deleteAll();
	}

}
