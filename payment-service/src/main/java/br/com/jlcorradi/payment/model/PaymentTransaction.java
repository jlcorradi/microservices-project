package br.com.jlcorradi.payment.model;

import br.com.jlcorradi.payment.PaymentMode;
import br.com.jlcorradi.payment.PaymentTransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction
{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID orderId;
  private UUID customerId;
  private Date transactionDate;
  private BigDecimal amount;
  @Enumerated(EnumType.STRING)
  private PaymentMode paymentMode;
  @Enumerated(EnumType.STRING)
  private PaymentTransactionStatus status;
  private UUID transactionCode;
  private String sanitizedCcNumber;
}
