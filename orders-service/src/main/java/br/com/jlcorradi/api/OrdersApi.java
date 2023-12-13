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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersApi {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody @Validated CreateOrderRequest request,
            BasicJwtAuthenticationToken principal) {
        OrderDto order = orderService.createOrder(request, UUID.fromString(principal.getUserId()));
        log.info("Order created: {}", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
