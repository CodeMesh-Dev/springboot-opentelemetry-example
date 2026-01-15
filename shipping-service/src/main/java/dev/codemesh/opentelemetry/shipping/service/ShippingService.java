package dev.codemesh.opentelemetry.shipping.service;

import dev.codemesh.opentelemetry.shipping.model.Shipment;
import dev.codemesh.opentelemetry.shipping.model.ShippingRequest;
import dev.codemesh.opentelemetry.shipping.model.ShippingResponse;
import dev.codemesh.opentelemetry.shipping.model.ShippingStatus;
import dev.codemesh.opentelemetry.shipping.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    
    private final ShipmentRepository shipmentRepository;
    private final Random random;

    @Autowired
    public ShippingService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
        this.random = new Random();
    }

    @Transactional
    public ShippingResponse createShipment(ShippingRequest request) {
        logger.info("Creating shipment for order: {}, address: {}", request.getOrderId(), request.getAddress());

        // Generate tracking number
        String trackingNumber = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Calculate estimated delivery (3-7 days from now)
        int daysToDeliver = 3 + random.nextInt(5); // 3 to 7 days
        LocalDateTime estimatedDelivery = LocalDateTime.now().plusDays(daysToDeliver);

        // Create and save shipment record
        Shipment shipment = Shipment.builder()
                .orderId(request.getOrderId())
                .trackingNumber(trackingNumber)
                .address(request.getAddress())
                .customerName(request.getCustomerName())
                .status(ShippingStatus.PENDING)
                .estimatedDelivery(estimatedDelivery)
                .build();

        shipmentRepository.save(shipment);

        logger.info("Shipment created for order: {} with tracking: {}, estimated delivery: {}", 
                request.getOrderId(), trackingNumber, estimatedDelivery);

        return ShippingResponse.builder()
                .trackingNumber(trackingNumber)
                .estimatedDelivery(estimatedDelivery)
                .orderId(request.getOrderId())
                .message("Shipment created successfully")
                .build();
    }
}
