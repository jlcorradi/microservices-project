package br.com.jlcorradi.payment.dto;

import br.com.jlcorradi.payment.PaymentMode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePaymentTransactionRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentMode paymentMode;
    @NotNull
    private PaymentInfo paymentInfo;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PaymentInfo {
        @NotNull
        private String ccNumber;
        @NotNull
        private String ccHolderName;
        @NotNull
        private String ccExpiration;
        @NotNull
        private String ccCvv;
    }
}
