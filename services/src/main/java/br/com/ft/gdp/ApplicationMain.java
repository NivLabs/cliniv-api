package br.com.ft.gdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.ft.gdp.controller.ResponsibleController;

@SpringBootApplication(scanBasePackages = "br.com.ft.gdp.models", scanBasePackageClasses = {ResponsibleController.class})
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
