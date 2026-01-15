package dev.codemesh.opentelemetry.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingResponse {
    private String trackingNumber;
    private LocalDateTime estimatedDelivery;
    private String orderId;
    private String message;
}
