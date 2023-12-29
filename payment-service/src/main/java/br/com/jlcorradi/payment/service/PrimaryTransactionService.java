package br.com.jlcorradi.payment.service;

import br.com.jlcorradi.commons.EntityDtoMapper;
import br.com.jlcorradi.payment.PaymentTransactionStatus;
import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;
import br.com.jlcorradi.payment.event.PaymentEventPublisher;
import br.com.jlcorradi.payment.model.PaymentTransaction;
import br.com.jlcorradi.payment.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PrimaryTransactionService implements PaymentTransactionService
{
  private final PaymentTransactionRepository repository;
  private final ModelMapper mapper;
  private final PaymentEventPublisher paymentEventPublisher;

  private final EntityDtoMapper<PaymentTransaction, PaymentTransactionDto> paymentTransactionDtoEntityDtoMapper;

  @Override
  public PaymentTransactionDto createPaymentTransaction(CreatePaymentTransactionRequest request, UUID userId)
  {
    PaymentTransaction paymentTransaction = mapper.map(request, PaymentTransaction.class);

    String sanitizedCcNumber = request.getPaymentInfo().getCcNumber().replaceAll("(?<!^..).(?=.{3})", "*");
    paymentTransaction.setSanitizedCcNumber(sanitizedCcNumber);
    paymentTransaction.setTransactionDate(new Date());
    paymentTransaction.setStatus(PaymentTransactionStatus.ACCEPTED);
    paymentTransaction.setTransactionCode(UUID.randomUUID());
    paymentTransaction.setCustomerId(userId);

    PaymentTransaction newPayment = repository.save(paymentTransaction);

    paymentEventPublisher.publishPaymentStatusChange(paymentTransaction);

    return paymentTransactionDtoEntityDtoMapper.toDto(newPayment, PaymentTransactionDto.class);
  }
}
