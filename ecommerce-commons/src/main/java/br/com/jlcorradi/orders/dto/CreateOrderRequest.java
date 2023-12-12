package br.com.jlcorradi.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateOrderRequest {
    @NotNull
    private UUID customerId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String history;
}
