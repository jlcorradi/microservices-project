package br.com.jlcorradi;

import br.com.jlcorradi.model.Order;
import br.com.jlcorradi.orders.dto.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.jlcorradi.orders.OrdersRoutingConstants.EVENT_ORDER_STATUS_CHANGE_ROUTING_KEY;

@Slf4j
@Component
public class RabbitOrderEventPublisher implements OrderEventPublisher
{
  private final RabbitTemplate rabbitTemplate;
  private final String ecommerceEventExchangeName;

  public RabbitOrderEventPublisher(
      RabbitTemplate rabbitTemplate, @Value("#{ecommerceEventExchange.name}") String ecommerceEventExchangeName)
  {
    this.rabbitTemplate = rabbitTemplate;
    this.ecommerceEventExchangeName = ecommerceEventExchangeName;
  }

  @Override
  public void publishOrderStatusChange(Order order)
  {
    log.info("Publishing order status change. #Order: {}, Status: {}", order.getId(), order.getStatus());
    OrderStatusChangeEvent event = OrderStatusChangeEvent.builder()
        .orderId(order.getId())
        .newStatus(order.getStatus())
        .build();
    rabbitTemplate.convertAndSend(ecommerceEventExchangeName, EVENT_ORDER_STATUS_CHANGE_ROUTING_KEY, event);
  }
}
