package br.com.jlcorradi.payment.service;

import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;

import java.util.UUID;

public interface PaymentTransactionService
{
  PaymentTransactionDto createPaymentTransaction(CreatePaymentTransactionRequest request, UUID userId);
}
