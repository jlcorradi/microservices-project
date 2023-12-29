package br.com.jlcorradi.payment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentRoutingConstants
{
  public static final String PAYMENTS_API_URL = "/api/v1/payments";
  public static final String PAYMENT_STATUS_CHANGE_ROUTING_KEY = "payment.onPaymentStatusChange";

}
