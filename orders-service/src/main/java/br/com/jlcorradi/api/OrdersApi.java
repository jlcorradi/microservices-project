package br.com.jlcorradi.api;

import br.com.jlcorradi.commons.auth.BasicJwtAuthenticationToken;
import br.com.jlcorradi.orders.dto.CreateOrderRequest;
import br.com.jlcorradi.orders.dto.OrderDto;
import br.com.jlcorradi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.jlcorradi.orders.OrdersRoutingConstants.ORDERS_API_V1_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(ORDERS_API_V1_URL)
public class OrdersApi
{

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(
      @RequestBody @Validated CreateOrderRequest request,
      BasicJwtAuthenticationToken principal)
  {
    OrderDto order = orderService.placeOrder(request, UUID.fromString(principal.getUserId()));
    log.info("Order created: {}", order);
    return ResponseEntity.status(HttpStatus.CREATED).body(order);
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> listPendingOrders(BasicJwtAuthenticationToken principal)
  {
    List<OrderDto> orders = orderService.listPendingOrders(UUID.fromString(principal.getUserId()));
    return ResponseEntity.ok(orders);
  }

}
