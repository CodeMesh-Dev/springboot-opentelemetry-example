package dev.codemesh.opentelemetry.orderservice;

import dev.codemesh.opentelemetry.orderservice.model.OrderRequest;
import dev.codemesh.opentelemetry.orderservice.model.OrderResponse;
import dev.codemesh.opentelemetry.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/order")
public class OrderRestController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private OrderService orderService;

  @PostMapping()
  public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
    logger.info("Received order request with amount: {}", request.getTotalAmount());
    
    OrderResponse response = orderService.createOrder(request);
    
    logger.info("Order response: {}", response.getStatus());
    
    return ResponseEntity.ok(response);
  }
}