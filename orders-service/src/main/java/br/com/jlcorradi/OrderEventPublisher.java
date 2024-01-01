package br.com.jlcorradi;

import br.com.jlcorradi.model.Order;

public interface OrderEventPublisher
{
  void publishOrderStatusChange(Order order);
}
