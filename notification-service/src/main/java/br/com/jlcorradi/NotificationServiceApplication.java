package br.com.jlcorradi;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@WithStaticJwtSecurity
@WithCommons
public class NotificationServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication springApplication = new SpringApplication(NotificationServiceApplication.class);
    springApplication.addListeners(new SystemInfoApplicationListener());
    springApplication.run(args);
  }
}

@Configuration
class EventConfig
{
  private final String paymentStatusChangeQueue;
  private final String onPaymentStatusChangeRoutingKey;
  private final Exchange exchange;

  EventConfig(@Value("${eventArchitecture.payment-status-change-notification-queue}") String paymentStatusChangeQueue,
              @Value("${eventArchitecture.payment-status-change-routing-key}") String onPaymentStatusChangeRoutingKey,
              Exchange exchange)
  {
    this.paymentStatusChangeQueue = paymentStatusChangeQueue;
    this.onPaymentStatusChangeRoutingKey = onPaymentStatusChangeRoutingKey;
    this.exchange = exchange;
  }

  @Bean
  public Queue onPaymentStatusChangeQueue()
  {
    return new Queue(paymentStatusChangeQueue);
  }

  @Bean
  public Binding onPaymentStatusChangeQueueBinding()
  {
    return BindingBuilder
        .bind(onPaymentStatusChangeQueue())
        .to(exchange)
        .with(onPaymentStatusChangeRoutingKey)
        .noargs();
  }
}