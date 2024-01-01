package br.com.jlcorradi;

import br.com.jlcorradi.commons.SystemInfoApplicationListener;
import br.com.jlcorradi.commons.config.WithCommons;
import br.com.jlcorradi.commons.config.WithStaticJwtSecurity;
import br.com.jlcorradi.payment.PaymentRoutingConstants;
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
  private final Exchange ecommerceEventExchange;

  EventConfig(@Value("${eventArchitecture.notificationOnPaymentStatusChangeQueue}") String paymentStatusChangeQueue,
              Exchange ecommerceEventExchange)
  {
    this.paymentStatusChangeQueue = paymentStatusChangeQueue;
    this.ecommerceEventExchange = ecommerceEventExchange;
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
        .to(ecommerceEventExchange)
        .with(PaymentRoutingConstants.EVENT_PAYMENT_STATUS_CHANGE_ROUTING_KEY)
        .noargs();
  }
}