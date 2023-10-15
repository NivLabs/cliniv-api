package br.com.nivlabs.cliniv;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationMain {

    public static AppSettings SETTINGS;
    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(AMERICA_SAO_PAULO));
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

}