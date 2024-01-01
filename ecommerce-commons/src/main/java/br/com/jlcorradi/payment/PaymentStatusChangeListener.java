package br.com.jlcorradi.payment;

import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;

public interface PaymentStatusChangeListener
{
  void onPaymentStatusChange(PaymentStatusChangeEvent event);
}
