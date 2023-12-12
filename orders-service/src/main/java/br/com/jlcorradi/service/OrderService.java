package br.com.jlcorradi.service;

import br.com.jlcorradi.orders.dto.CreateOrderRequest;
import br.com.jlcorradi.orders.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequest order);
}
