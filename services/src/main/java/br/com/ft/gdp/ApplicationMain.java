package br.com.ft.gdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.ft.gdp.config.SwaggerConfig;
import br.com.ft.gdp.controller.ResponsibleController;
import br.com.ft.gdp.service.ResponsibleService;

@SpringBootApplication(scanBasePackages = "br.com.ft.gdp.models", scanBasePackageClasses = {ResponsibleController.class,
                                                                                            ResponsibleService.class,
                                                                                            SwaggerConfig.class})
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
