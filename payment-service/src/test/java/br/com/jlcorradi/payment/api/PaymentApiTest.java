package br.com.jlcorradi.payment.api;

import br.com.jlcorradi.commons.IntegrationTestBaseContext;
import br.com.jlcorradi.commons.ServiceTest;
import br.com.jlcorradi.payment.PaymentTransactionStatus;
import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static br.com.jlcorradi.commons.utilities.TestUtils.toJson;
import static br.com.jlcorradi.payment.DummyObjectGenerator.paymentTransactionRequest;
import static br.com.jlcorradi.payment.PaymentRoutingConstants.EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Import(IntegrationTestBaseContext.class)
class PaymentApiTest extends ServiceTest {

  @MockBean
  RabbitTemplate rabbitTemplate;

  @Captor
  private ArgumentCaptor<String> exchangeCaptor;

  @Captor
  private ArgumentCaptor<String> routingKeyCaptor;

  @Captor
  private ArgumentCaptor<PaymentStatusChangeEvent> messageCaptor;

  @Override
  @BeforeEach
  protected void setup() {
    withRoles("ROLE_user");
    log.info("Setting up stuff in the subclass. Testing if superclass functionality will be preserved.");
  }

  @Test
  @DisplayName("Create payment successfully")
  void shouldCreatePaymentSuccessfully() throws Exception {
    // GIVEN
    CreatePaymentTransactionRequest request = paymentTransactionRequest(x -> x);

    // WHEN
    ResultActions resultActions = mockMvc.perform(post("/api/v1/payments")
        .contentType(APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        .content(toJson(request)));

    // THEN
    resultActions
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.amount").value("1988.23"))
        .andExpect(jsonPath("$.status").value(PaymentTransactionStatus.ACCEPTED.toString()));

    verify(rabbitTemplate).convertAndSend(exchangeCaptor.capture(),
        routingKeyCaptor.capture(),
        messageCaptor.capture()
    );
    assertEquals(EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY, routingKeyCaptor.getValue());
    PaymentStatusChangeEvent message = messageCaptor.getValue();
    assertNotNull(message.getPaymentTransactionId());
    assertEquals(PaymentTransactionStatus.ACCEPTED, message.getStatus());
    assertNotNull(message.getTransactionCode());
  }
}