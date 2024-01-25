package br.com.jlcorradi.payment.repository;

import br.com.jlcorradi.payment.model.PaymentTransaction;

import java.util.UUID;

public interface PaymentTransactionRepository extends BaseRepository<PaymentTransaction, UUID> {

}
