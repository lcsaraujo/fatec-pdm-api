package br.edu.fatec.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.edu.fatec")
public class AulaApplication {
    public static void main(String[] args){
        SpringApplication.run(AulaApplication.class);

    }
}
