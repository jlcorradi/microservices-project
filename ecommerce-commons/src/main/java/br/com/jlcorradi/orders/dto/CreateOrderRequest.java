package br.com.jlcorradi.orders.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String history;
}
