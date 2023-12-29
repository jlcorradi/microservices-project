package br.com.jlcorradi.event;

import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;

public interface PaymentStatusChangeListener
{
  void onPaymentStatusChange(PaymentStatusChangeEvent payload);
}
