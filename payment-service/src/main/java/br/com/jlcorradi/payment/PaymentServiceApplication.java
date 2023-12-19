package br.com.jlcorradi.payment;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@WithCommons
@WithStaticJwtSecurity
@SpringBootApplication
public class PaymentServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication springApplication = new SpringApplication(PaymentServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }
}