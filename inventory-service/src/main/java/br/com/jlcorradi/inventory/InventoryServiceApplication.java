package br.com.jlcorradi.inventory;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@WithCommons
@WithStaticJwtSecurity
@SpringBootApplication
public class InventoryServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication application = new SpringApplication(InventoryServiceApplication.class);
    application.addListeners(new SystemInfoApplicationListener());
    application.run(args);
  }
}
