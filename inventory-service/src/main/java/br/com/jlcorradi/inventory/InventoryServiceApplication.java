package br.com.jlcorradi.inventory;

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
    SpringApplication.run(InventoryServiceApplication.class, args);
  }
}
