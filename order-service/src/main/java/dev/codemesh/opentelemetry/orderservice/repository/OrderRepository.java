package dev.codemesh.opentelemetry.orderservice.repository;

import dev.codemesh.opentelemetry.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findByOrderId(String orderId);
}
