package br.com.jlcorradi.payment.event;

import br.com.jlcorradi.payment.model.PaymentTransaction;

public interface PaymentEventPublisher {
  void publishPaymentStatusChange(PaymentTransaction paymentTransaction);
}
