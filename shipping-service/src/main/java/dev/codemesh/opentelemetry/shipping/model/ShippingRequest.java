package dev.codemesh.opentelemetry.shipping.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Address is required")
    private String address;

    private String customerName;
}
