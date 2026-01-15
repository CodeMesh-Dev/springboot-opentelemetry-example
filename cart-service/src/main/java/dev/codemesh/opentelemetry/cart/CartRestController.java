package dev.codemesh.opentelemetry.cart;

import dev.codemesh.opentelemetry.cart.model.CartCheckoutRequest;
import dev.codemesh.opentelemetry.cart.model.OrderRequest;
import dev.codemesh.opentelemetry.cart.model.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("api/cart")
public class CartRestController {

  @Autowired
  private OrderServiceClient orderServiceClient;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/checkout")
  public ResponseEntity<OrderResponse> checkout(@Valid @RequestBody CartCheckoutRequest request) {
    logger.info("Checking out cart with amount: {}", request.getTotalAmount());
    
    // Convert cart request to order request
    OrderRequest orderRequest = OrderRequest.builder()
            .items(request.getItems())
            .totalAmount(request.getTotalAmount())
            .shippingAddress(request.getShippingAddress())
            .customerName(request.getCustomerName())
            .build();
    
    OrderResponse response = orderServiceClient.createOrder(orderRequest);
    
    logger.info("Checkout response: {}", response.getStatus());
    return ResponseEntity.ok(response);
  }
}