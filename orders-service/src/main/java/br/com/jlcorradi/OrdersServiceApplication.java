package br.com.jlcorradi;

import br.com.jlcorradi.commons.auth.WithStaticJwtSecurity;
import br.com.jlcorradi.commons.config.WithCommons;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@WithCommons
@WithStaticJwtSecurity
public class OrdersServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }
}