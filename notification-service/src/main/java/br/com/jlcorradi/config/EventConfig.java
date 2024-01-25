package br.com.jlcorradi.config;

import br.com.jlcorradi.orders.OrdersRoutingConstants;
import br.com.jlcorradi.payment.PaymentRoutingConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EventConfig {
  private final String paymentStatusChangeQueue;
  private final String orderStatusChangeQueue;
  private final Exchange ecommerceEventExchange;

  EventConfig(@Value("${eventArchitecture.notificationOnPaymentStatusChangeQueue}") String paymentStatusChangeQueue,
              @Value("${eventArchitecture.notificationOnOrderStatusChangeQueue}") String orderStatusChangeQueue,
              Exchange ecommerceEventExchange) {
    this.paymentStatusChangeQueue = paymentStatusChangeQueue;
    this.orderStatusChangeQueue = orderStatusChangeQueue;
    this.ecommerceEventExchange = ecommerceEventExchange;
  }

  @Bean
  public Queue onPaymentStatusChangeQueue() {
    return new Queue(paymentStatusChangeQueue);
  }

  @Bean
  Queue onOrderStatusChangeQueue() {
    return new Queue(orderStatusChangeQueue);
  }

  @Bean
  public Binding onPaymentStatusChangeQueueBinding(@Value("#{onPaymentStatusChangeQueue}") Queue onPaymentStatusChangeQueue) {
    return BindingBuilder
        .bind(onPaymentStatusChangeQueue)
        .to(ecommerceEventExchange)
        .with(PaymentRoutingConstants.EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY)
        .noargs();
  }

  @Bean
  public Binding onOrderStatusChangeQueueBinding(@Value("#{onOrderStatusChangeQueue}") Queue onOrderStatusChangeQueue) {
    return BindingBuilder
        .bind(onOrderStatusChangeQueue)
        .to(ecommerceEventExchange)
        .with(OrdersRoutingConstants.EVENT_ORDER_STATUS_CHANGE_ROUTING_KEY)
        .noargs();
  }
}