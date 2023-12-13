package br.com.jlcorradi.payment.dto;

import br.com.jlcorradi.payment.PaymentMode;
import br.com.jlcorradi.payment.PaymentTransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class PaymentTransactionDto {
    private UUID orderId;
    private UUID customerId;
    private Date transactionDate;
    private BigDecimal amount;
    private PaymentMode paymentMode;

    private PaymentTransactionStatus status;
    private UUID transactionCode;
    private String sanitizedCcNumber;
}
