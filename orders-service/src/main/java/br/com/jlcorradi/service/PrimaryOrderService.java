package br.com.jlcorradi.service;

import br.com.jlcorradi.OrderEventPublisher;
import br.com.jlcorradi.commons.exception.EcommerceExcepion;
import br.com.jlcorradi.commons.exception.EntityNotFoundException;
import br.com.jlcorradi.dao.OrderRepository;
import br.com.jlcorradi.model.Order;
import br.com.jlcorradi.orders.OrderStatus;
import br.com.jlcorradi.orders.dto.CreateOrderRequest;
import br.com.jlcorradi.orders.dto.OrderDto;
import br.com.jlcorradi.payment.PaymentMode;
import br.com.jlcorradi.payment.PaymentStatusChangeListener;
import br.com.jlcorradi.payment.client.PaymentClient;
import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrimaryOrderService implements OrderService, PaymentStatusChangeListener {
  private final OrderRepository orderRepository;
  private final ModelMapper mapper;
  private final PaymentClient paymentClient;
  private final OrderEventPublisher orderEventPublisher;

  @Override
  public OrderDto placeOrder(CreateOrderRequest order, UUID userId) {
    Order newOrder = createOrder(order, userId);
    try {
      PaymentTransactionDto paymentTransactionDto = paymentClient.makePayment(resolvePaymentTransactionRequest(newOrder));
      newOrder = updatePaymentInfo(newOrder, paymentTransactionDto.getTransactionCode());
      orderEventPublisher.publishOrderStatusChange(newOrder);
    } catch (Exception e) {
      newOrder.setStatus(OrderStatus.AWAITING_PAYMENT);
      newOrder = orderRepository.save(newOrder);
      orderEventPublisher.publishOrderStatusChange(newOrder);
    }
    return mapper.map(newOrder, OrderDto.class);
  }

  private Order updatePaymentInfo(Order newOrder, UUID transactionCode) {
    newOrder.setStatus(OrderStatus.AWAITING_INVOICE);
    newOrder.setPaymentTransactionId(transactionCode);
    return orderRepository.save(newOrder);
  }

  private CreatePaymentTransactionRequest resolvePaymentTransactionRequest(Order newOrder) {
    return CreatePaymentTransactionRequest.builder()
        .orderId(newOrder.getId())
        .amount(newOrder.getAmount())
        .paymentMode(PaymentMode.CREDIT_CARD)
        .paymentInfo(
            CreatePaymentTransactionRequest.PaymentInfo.builder()
                .ccNumber("555 5463 2334 9889")
                .ccCvv("123")
                .ccExpiration("1024")
                .ccHolderName(newOrder.getHistory())
                .build())
        .build();
  }

  private Order createOrder(CreateOrderRequest order, UUID userId) {
    Order newOrder = mapper.map(order, Order.class);
    newOrder.setCustomerId(userId);
    newOrder.setStatus(OrderStatus.PENDING);
    newOrder.setOrderDate(new Date());
    return orderRepository.save(newOrder);
  }

  @Override
  public List<OrderDto> listPendingOrders(UUID uuid) {
    return orderRepository.findAllByCustomerIdAndStatusNot(uuid, OrderStatus.COMPLETE).stream()
        .map(order -> mapper.map(order, OrderDto.class))
        .toList();
  }

  @Override
  public Optional<OrderDto> getOrder(UUID orderId) {
    return Optional.ofNullable(orderRepository.findById(orderId)
        .map(order -> mapper.map(Order.class, OrderDto.class))
        .orElseThrow(EntityNotFoundException::new));
  }

  @Override
  @RabbitListener(queues = "#{orderPaymentReceivedQueue.name}")
  public void onPaymentStatusChange(PaymentStatusChangeEvent event) {
    log.info("Handling onPaymentStatusChange. Payment Transaction Id: {}, status:{}",
        event.getPaymentTransactionId(), event.getStatus());

    orderRepository.findByPaymentTransactionId(event.getPaymentTransactionId()).ifPresent(order ->
    {
      switch (event.getStatus()) {
        case ACCEPTED -> order.setStatus(OrderStatus.AWAITING_INVOICE);
        case REJECTED -> order.setStatus(OrderStatus.AWAITING_PAYMENT);
        case REFUNDED -> order.setStatus(OrderStatus.CANCELED);
        default -> throw new EcommerceExcepion("Illegal state change", null);
      }
      orderEventPublisher.publishOrderStatusChange(order);
    });
  }
}
