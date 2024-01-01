package br.com.jlcorradi.orders;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersRoutingConstants
{
  public static final String ORDERS_API_V1_URL= "/api/v1/orders";
  public static final String EVENT_ORDER_STATUS_CHANGE_ROUTING_KEY = "event.order.onOrderStatusChange";
}
