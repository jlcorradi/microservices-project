package br.com.jlcorradi.orders;

import br.com.jlcorradi.orders.dto.OrderStatusChangeEvent;

public interface OrderStatusChangeEventListener
{
  void onOrderStatusChange(OrderStatusChangeEvent event);
}
