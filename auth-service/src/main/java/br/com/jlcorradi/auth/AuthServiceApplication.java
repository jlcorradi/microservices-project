package br.com.jlcorradi.auth;

import br.com.jlcorradi.auth.model.EcommerceUser;
import br.com.jlcorradi.auth.repository.EcommerceUserRepository;
import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@SpringBootApplication
@WithCommons
public class AuthServiceApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(AuthServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }

  @Bean
  public CommandLineRunner init(EcommerceUserRepository userRepository,
                                @Value("${app.example-user-name}") String exampleUserName,
                                @Value("${app.example-user-password}") String password,
                                PasswordEncoder passwordEncoder) {

    return args -> {
      Optional<EcommerceUser> exampleUser = userRepository.findByUsername(exampleUserName);
      if (exampleUser.isEmpty()) {
        EcommerceUser createdUser = userRepository.save(EcommerceUser.builder()
            .id(UUID.randomUUID())
            .username(exampleUserName)
            .commaSeparatedAuthorities("ROLE_USER,ROLE_ADMIN,ROLE_ROOT")
            .encodedPassword(passwordEncoder.encode(password))
            .build());
        log.info("Example User was created: ID: {}. username: {}", createdUser.getId(), createdUser.getUsername());
      }
    };
  }
}