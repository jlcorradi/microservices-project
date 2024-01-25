package br.com.jlcorradi.dao;

import br.com.jlcorradi.model.Order;
import br.com.jlcorradi.orders.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
  List<Order> findAllByCustomerIdAndStatusNot(UUID userId, OrderStatus statusOtherThan);

  Optional<Order> findByPaymentTransactionId(UUID paymentTransactionId);
}
