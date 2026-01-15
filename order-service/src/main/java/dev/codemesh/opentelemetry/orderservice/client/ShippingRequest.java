package dev.codemesh.opentelemetry.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRequest {
    private String orderId;
    private String address;
    private String customerName;
}
