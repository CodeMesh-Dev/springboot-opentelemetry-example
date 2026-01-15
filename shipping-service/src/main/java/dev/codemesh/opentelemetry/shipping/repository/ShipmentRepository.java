package dev.codemesh.opentelemetry.shipping.repository;

import dev.codemesh.opentelemetry.shipping.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    
    Optional<Shipment> findByOrderId(String orderId);
    
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
}
