package dev.codemesh.opentelemetry.shipping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderId;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private String address;

    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime estimatedDelivery;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
