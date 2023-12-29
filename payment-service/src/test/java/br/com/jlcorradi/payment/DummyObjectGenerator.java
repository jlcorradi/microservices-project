package br.com.jlcorradi.payment;

import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Function;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class DummyObjectGenerator
{
  public static CreatePaymentTransactionRequest paymentTransactionRequest(
      Function<CreatePaymentTransactionRequest.CreatePaymentTransactionRequestBuilder,
          CreatePaymentTransactionRequest.CreatePaymentTransactionRequestBuilder> x)
  {
    CreatePaymentTransactionRequest.CreatePaymentTransactionRequestBuilder builder =
        CreatePaymentTransactionRequest.builder();

    builder.paymentInfo(paymentInfo(y -> y))
        .paymentMode(PaymentMode.CREDIT_CARD)
        .amount(BigDecimal.valueOf(1988.23))
        .orderId(UUID.randomUUID());

    return x.apply(builder).build();
  }

  private static CreatePaymentTransactionRequest.PaymentInfo paymentInfo(
      Function<CreatePaymentTransactionRequest.PaymentInfo.PaymentInfoBuilder,
          CreatePaymentTransactionRequest.PaymentInfo.PaymentInfoBuilder> x)
  {
    CreatePaymentTransactionRequest.PaymentInfo.PaymentInfoBuilder builder =
        CreatePaymentTransactionRequest.PaymentInfo.builder();

    builder.ccNumber("1234123412341234")
        .ccHolderName("John Doe")
        .ccExpiration("12/20")
        .ccCvv("123")
        .ccType("VISA");

    return x.apply(builder).build();
  }
}