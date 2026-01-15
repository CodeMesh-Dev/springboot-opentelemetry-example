package dev.codemesh.opentelemetry.shipping.controller;

import dev.codemesh.opentelemetry.shipping.model.ShippingRequest;
import dev.codemesh.opentelemetry.shipping.model.ShippingResponse;
import dev.codemesh.opentelemetry.shipping.service.ShippingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shipping")
public class ShippingRestController {

    private static final Logger logger = LoggerFactory.getLogger(ShippingRestController.class);

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/create")
    public ResponseEntity<ShippingResponse> createShipment(@Valid @RequestBody ShippingRequest request) {
        logger.info("Received shipping request for order: {}", request.getOrderId());
        
        ShippingResponse response = shippingService.createShipment(request);
        
        logger.info("Shipping created with tracking: {}", response.getTrackingNumber());
        
        return ResponseEntity.ok(response);
    }
}
