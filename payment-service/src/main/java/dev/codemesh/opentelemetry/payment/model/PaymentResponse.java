package dev.codemesh.opentelemetry.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String transactionId;
    private boolean success;
    private String message;
    private String orderId;
}
