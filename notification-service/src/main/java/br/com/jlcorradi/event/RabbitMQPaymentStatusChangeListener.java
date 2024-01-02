package br.com.jlcorradi.event;

import br.com.jlcorradi.orders.OrderStatusChangeEventListener;
import br.com.jlcorradi.orders.client.OrdersClient;
import br.com.jlcorradi.orders.dto.OrderDto;
import br.com.jlcorradi.orders.dto.OrderStatusChangeEvent;
import br.com.jlcorradi.payment.PaymentStatusChangeListener;
import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQPaymentStatusChangeListener implements PaymentStatusChangeListener, OrderStatusChangeEventListener
{
  private final OrdersClient ordersClient;

  @Override
  @RabbitListener(queues = "#{onPaymentStatusChangeQueue.name}")
  public void onPaymentStatusChange(PaymentStatusChangeEvent event)
  {
    log.info("Handling onPaymentStatusChange {}", event);
  }

  @Override
  @RabbitListener(queues = "#{onOrderStatusChangeQueue.name}")
  public void onOrderStatusChange(OrderStatusChangeEvent event)
  {
    log.info("Handling onOrderStatusChange {}", event);
    ResponseEntity<OrderDto> response = ordersClient.getOrder(event.getOrderId());
    log.info("Order: {}", response);
  }
}
