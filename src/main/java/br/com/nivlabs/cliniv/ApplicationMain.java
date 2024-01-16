package br.com.nivlabs.cliniv;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@EnableCaching
@SpringBootApplication
public class ApplicationMain {
    public static final String AMERICA_SAO_PAULO;

    static {
        AMERICA_SAO_PAULO = "America/Sao_Paulo";
    }

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(AMERICA_SAO_PAULO));
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

}