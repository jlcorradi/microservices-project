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

/**
 * <p>
 * This Base test class is intended to be extended by classes that will serve as integration tests.
 * It provides out-of-the-box support for MockMvc invocations using the protected field mockMvc.
 * </p>
 * <p>
 * By default, it has a setup method that will accept all authentication via Bearer header with ROLE_user authority.
 * You can override that behavior by overriding the setup method or invoking the method withRoles(...) within your
 * own setup method.
 * <p/>
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Import(IntegrationTestBaseContext.class)
public abstract class ServiceTest {
  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected JwtValidator jwtValidator;

  @BeforeEach
  protected void setup() {
    withRoles("ROLE_user");
  }

  protected void withRoles(String commaSeparatedRoles) {
    Mockito.when(jwtValidator.validateJwtToken(ArgumentMatchers.anyString())).thenReturn(new DefaultClaims(Map.of(
        Claims.SUBJECT, "testuser@email.com",
        USER_ID_JWT_CLAIM, UUID.randomUUID().toString(),
        Claims.EXPIRATION, new Date(System.currentTimeMillis() + 60000),
        AUTHORITIES_JWT_CLAIM, commaSeparatedRoles)));

  }
}
