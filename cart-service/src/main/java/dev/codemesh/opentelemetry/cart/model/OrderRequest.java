package dev.codemesh.opentelemetry.cart.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<String> items;
    
    @NotNull
    @Positive
    private BigDecimal totalAmount;
    
    private String shippingAddress;
    private String customerName;
}
