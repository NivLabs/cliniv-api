package br.com.nivlabs.cliniv.service.appointment.business;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.repository.AppointmentRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeleteAppointmentBusinessHandler implements BaseBusinessHandler {

    private final Logger logger;
    private final AppointmentRepository repo;

    @Autowired
    public DeleteAppointmentBusinessHandler(final Logger logger,
                                            final AppointmentRepository repo) {
        this.logger = logger;
        this.repo = repo;
    }

    @Transactional
    public void execute(final Long id) {
        logger.info("Iniciando processo de remoção de agendamento :: {}", id);

        final var entity = repo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento com identificador %s não encontrado."));
        logger.info("Removendo agendamento de {}, para o profissional {} na Data/Hora {}", entity.getPatient().getPerson().getFullName(), entity.getProfessional().getPerson().getFullName(), entity.getAppointmentDateAndTime());
        repo.deleteById(id);
        repo.flush();
    }
}