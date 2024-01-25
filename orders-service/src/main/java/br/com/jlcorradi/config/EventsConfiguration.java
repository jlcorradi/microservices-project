package br.com.jlcorradi.config;

import br.com.jlcorradi.payment.PaymentRoutingConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
class EventsConfiguration {
  private final Exchange ecommerceEventExchange;

  @Bean
  public Queue orderPaymentReceivedQueue() {
    return new Queue("order_on_payment_status_change_queue");
  }

  @Bean
  public Binding orderPaymentReceivedQueueBinding(
      @Value("#{orderPaymentReceivedQueue}") Queue orderPaymentReceivedQueue) {
    return BindingBuilder.bind(orderPaymentReceivedQueue)
        .to(ecommerceEventExchange)
        .with(PaymentRoutingConstants.EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY)
        .noargs();
  }

}