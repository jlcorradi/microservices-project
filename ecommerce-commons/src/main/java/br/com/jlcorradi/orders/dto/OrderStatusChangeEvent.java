package br.com.jlcorradi.orders.dto;

import br.com.jlcorradi.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangeEvent
{
  private UUID orderId;
  private OrderStatus newStatus;
}
