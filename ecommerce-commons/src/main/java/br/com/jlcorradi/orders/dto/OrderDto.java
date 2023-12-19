package br.com.jlcorradi.orders.dto;

import br.com.jlcorradi.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto
{
  private UUID orderId;
  private UUID customerId;
  private Date orderDate;
  private BigDecimal amount;
  private String history;
  private OrderStatus status;
  private UUID paymentTransactionId;

}
