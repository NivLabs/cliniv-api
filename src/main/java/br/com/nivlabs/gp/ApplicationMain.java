package br.com.nivlabs.gp;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.nivlabs.gp.repository.ParameterRepository;

@SpringBootApplication
public class ApplicationMain {

    public static AppSettings SETTINGS;

    @Autowired
    private ParameterRepository parameterRepository;

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

    @PostConstruct
    private void loadParameter() {
        SETTINGS = new AppSettings(parameterRepository.findAll());
    }

    public static void reloadParameter(ParameterRepository parameterRepository) {
        SETTINGS = new AppSettings(parameterRepository.findAll());
    }

}