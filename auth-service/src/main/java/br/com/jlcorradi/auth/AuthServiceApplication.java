package br.com.jlcorradi.auth;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@WithCommons
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AuthServiceApplication.class);
        springApplication.addListeners(new SystemInfoApplicationListener());
        springApplication.run(args);
    }
}