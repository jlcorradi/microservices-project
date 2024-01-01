package br.com.jlcorradi.invoice.config;

import br.com.jlcorradi.orders.OrdersRoutingConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
class MessagingConfig
{
  private final Exchange ecommerceEventExchange;

  @Bean
  public Queue inventoryOnOrderStatusChangeQueue()
  {
    return new Queue("invoice_on_order_status_change_queue");
  }

  @Bean
  public Binding inventoryOnOrderStatusChangeQueueBinding(
      Queue inventoryOnOrderStatusChangeQueueBinding
  )
  {
    return BindingBuilder.bind(inventoryOnOrderStatusChangeQueueBinding)
        .to(ecommerceEventExchange)
        .with(OrdersRoutingConstants.EVENT_ORDER_STATUS_CHANGE_ROUTING_KEY)
        .noargs();
  }
}
