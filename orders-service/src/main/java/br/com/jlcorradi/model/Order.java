package br.com.jlcorradi.model;

import br.com.jlcorradi.orders.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
@Table(name = "ecommerce_order")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID customerId;
    private Date orderDate;
    private BigDecimal amount;
    private String history;
    private UUID paymentTransactionId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
