package br.com.jlcorradi.payment.api;

import br.com.jlcorradi.commons.auth.BasicJwtAuthenticationToken;
import br.com.jlcorradi.payment.dto.CreatePaymentTransactionRequest;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;
import br.com.jlcorradi.payment.service.PaymentTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentApi {
    private final PaymentTransactionService paymentTransactionService;

    @PostMapping
    public ResponseEntity<PaymentTransactionDto> createPaymentTransaction(
            @Valid @RequestBody CreatePaymentTransactionRequest request,
            BasicJwtAuthenticationToken principal
    ) {
        PaymentTransactionDto paymentTransaction = paymentTransactionService.createPaymentTransaction(request,
                UUID.fromString(principal.getUserId()));
        return ResponseEntity.ok(paymentTransaction);
    }
}
