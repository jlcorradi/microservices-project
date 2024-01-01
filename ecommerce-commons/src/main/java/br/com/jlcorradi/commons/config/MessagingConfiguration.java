package br.com.jlcorradi.commons.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

@Configuration
@Conditional(MessagingConfigurationRabbitSet.class)
public class MessagingConfiguration
{
  @Bean
  Jackson2JsonMessageConverter messageConverter()
  {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Exchange ecommerceEventExchange(
      @Value("${eventArchitecture.ecommerceEventExchange}") String ecommerceEventTopicName)
  {
    return new TopicExchange(ecommerceEventTopicName);
  }
}

class MessagingConfigurationRabbitSet implements Condition
{
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
  {
    return StringUtils.hasText(context.getEnvironment().getProperty(("spring.rabbitmq.addresses")));
  }
}