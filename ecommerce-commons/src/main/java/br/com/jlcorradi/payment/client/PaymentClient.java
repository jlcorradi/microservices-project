package br.com.jlcorradi.payment.client;

import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static br.com.jlcorradi.payment.PaymentRoutingConstants.PAYMENTS_API_URL;

@FeignClient(name = "paymentClient", url = "${client-services-urls.payment-service}" + PAYMENTS_API_URL)
public interface PaymentClient
{
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  PaymentTransactionDto makePayment(@RequestBody CreatePaymentTransactionRequest request);
}
