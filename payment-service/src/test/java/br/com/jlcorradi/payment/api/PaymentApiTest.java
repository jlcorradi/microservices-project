package br.com.jlcorradi.payment.api;

import br.com.jlcorradi.commons.IntegrationTestBaseContext;
import br.com.jlcorradi.commons.ServiceTest;
import br.com.jlcorradi.payment.PaymentTransactionStatus;
import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static br.com.jlcorradi.commons.utilities.TestUtils.toJson;
import static br.com.jlcorradi.payment.DummyObjectGenerator.paymentTransactionRequest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(IntegrationTestBaseContext.class)
class PaymentApiTest extends ServiceTest
{
  @Test
  @DisplayName("Create payment successfully")
  void shouldCreatePaymentSuccessfully() throws Exception
  {
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

  }
}