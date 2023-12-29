package br.com.jlcorradi.payment.dto;

import br.com.jlcorradi.payment.PaymentTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusChangeEvent
{
  private UUID paymentTransactionId;
  private PaymentTransactionStatus status;
  private UUID transactionCode;
}
