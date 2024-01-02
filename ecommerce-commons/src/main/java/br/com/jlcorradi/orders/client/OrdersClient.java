package br.com.jlcorradi.orders.client;

import br.com.jlcorradi.orders.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

import static br.com.jlcorradi.orders.OrdersRoutingConstants.ORDERS_API_V1_URL;

@FeignClient(name = "ordersClient", url = "${client-services-urls.order-service:orders}" + ORDERS_API_V1_URL)
public interface OrdersClient
{
  @GetMapping(value = "{orderId}")
  ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") UUID orderId);
}
