package br.com.jlcorradi.commons;

import br.com.jlcorradi.commons.auth.JwtValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static br.com.jlcorradi.commons.auth.Constants.AUTHORITIES_JWT_CLAIM;
import static br.com.jlcorradi.commons.auth.Constants.USER_ID_JWT_CLAIM;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Import(IntegrationTestBaseContext.class)
public abstract class ServiceTest
{
  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected JwtValidator jwtValidator;

  @BeforeEach
  void setup()
  {
    Mockito.when(jwtValidator.validateJwtToken(ArgumentMatchers.anyString())).thenReturn(new DefaultClaims(Map.of(
        Claims.SUBJECT, "testuser@email.com",
        USER_ID_JWT_CLAIM, UUID.randomUUID().toString(),
        Claims.EXPIRATION, new Date(System.currentTimeMillis() + 60000),
        AUTHORITIES_JWT_CLAIM, "user")));

  }
}
