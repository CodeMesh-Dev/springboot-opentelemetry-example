package dev.codemesh.opentelemetry.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String orderId;
    private String status;
    private String message;
    private String trackingNumber;
    private String paymentTransactionId;
}
