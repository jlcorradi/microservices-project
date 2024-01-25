package br.com.jlcorradi.commons;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class IntegrationTestBaseContext {

  @Bean
  public Exchange ecommerceEventExchange() {
    return new FanoutExchange("test.exchange");
  }

  @Bean
  Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
