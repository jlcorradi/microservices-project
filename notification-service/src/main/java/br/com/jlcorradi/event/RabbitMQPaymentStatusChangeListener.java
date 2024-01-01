package br.com.jlcorradi.event;

import br.com.jlcorradi.payment.PaymentStatusChangeListener;
import br.com.jlcorradi.payment.dto.PaymentStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQPaymentStatusChangeListener implements PaymentStatusChangeListener
{
  @Override
  @RabbitListener(queues = "#{onPaymentStatusChangeQueue.name}")
  public void onPaymentStatusChange(PaymentStatusChangeEvent event)
  {
    log.info("Processing event {}", event);
  }
}
