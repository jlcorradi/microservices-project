package br.com.jlcorradi.payment.repository;

import br.com.jlcorradi.payment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID>
{
}
