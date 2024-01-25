package br.com.jlcorradi.payment.event;

import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;
import br.com.jlcorradi.payment.model.PaymentTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.jlcorradi.payment.PaymentRoutingConstants.EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY;

@Slf4j
@Component
public class RabbitMQPaymentEventPublisher implements PaymentEventPublisher {
  private final RabbitTemplate rabbitTemplate;
  private final String ecommerceExchangeTopicName;

  public RabbitMQPaymentEventPublisher(
      RabbitTemplate rabbitTemplate,
      @Value("#{ecommerceEventExchange.name}") String ecommerceExchangeTopicName) {
    this.rabbitTemplate = rabbitTemplate;
    this.ecommerceExchangeTopicName = ecommerceExchangeTopicName;
  }

  @Override
  public void publishPaymentStatusChange(PaymentTransaction paymentTransaction) {
    PaymentStatusChangeEvent payload = PaymentStatusChangeEvent.builder()
        .paymentTransactionId(paymentTransaction.getId())
        .status(paymentTransaction.getStatus())
        .transactionCode(paymentTransaction.getTransactionCode())
        .build();
    log.info("Notifying of Payment Status Change: {}", payload);
    rabbitTemplate.convertAndSend(ecommerceExchangeTopicName, EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY, payload);
  }
}
