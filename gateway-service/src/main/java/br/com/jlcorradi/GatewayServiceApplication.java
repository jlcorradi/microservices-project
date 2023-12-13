package br.com.jlcorradi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatewayServiceApplication.class);
        springApplication.run(args);
    }
}