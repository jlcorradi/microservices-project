package br.com.jlcorradi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayServiceApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(GatewayServiceApplication.class);
    springApplication.run(args);
  }

  @Bean
  public WebSessionManager webSessionManager() {
    // Emulate SessionCreationPolicy.STATELESS
    return exchange -> Mono.empty();
  }
}