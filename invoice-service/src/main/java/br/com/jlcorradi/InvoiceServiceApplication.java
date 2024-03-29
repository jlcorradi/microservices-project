package br.com.jlcorradi;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@WithCommons
@WithStaticJwtSecurity
public class InvoiceServiceApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(InvoiceServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }
}