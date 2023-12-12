package br.com.jlcorradi.service;

import br.com.jlcorradi.dao.OrderRepository;
import br.com.jlcorradi.model.Order;
import br.com.jlcorradi.orders.OrderStatus;
import br.com.jlcorradi.orders.dto.CreateOrderRequest;
import br.com.jlcorradi.orders.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrimaryOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest order) {
        Order newOrder = mapper.map(order, Order.class);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(new Date());
        newOrder = orderRepository.save(newOrder);
        return mapper.map(newOrder, OrderDto.class);
    }
}
