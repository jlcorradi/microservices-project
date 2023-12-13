package br.com.jlcorradi.service;

import br.com.jlcorradi.orders.dto.CreateOrderRequest;
import br.com.jlcorradi.orders.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequest order, UUID userId);

    List<OrderDto> listPendingOrders(UUID uuid);
}
