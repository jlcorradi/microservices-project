package br.com.jlcorradi;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@WithCommons
@WithStaticJwtSecurity
@EnableFeignClients
public class OrdersServiceApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(OrdersServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }
}