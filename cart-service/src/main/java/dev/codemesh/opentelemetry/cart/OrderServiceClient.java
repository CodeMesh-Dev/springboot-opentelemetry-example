package dev.codemesh.opentelemetry.cart;

import dev.codemesh.opentelemetry.cart.model.OrderRequest;
import dev.codemesh.opentelemetry.cart.model.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceClient.class);

    private final RestTemplate restTemplate;
    private final String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate,
                              @Value("${order_url}") String orderServiceUrl) {
        this.restTemplate = restTemplate;
        this.orderServiceUrl = orderServiceUrl;
    }

    public OrderResponse createOrder(OrderRequest request) {
        String url = orderServiceUrl + "/api/order";
        
        logger.info("Calling order service with amount: {}", request.getTotalAmount());
        
        try {
            ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
                    url, request, OrderResponse.class);
            
            logger.info("Order service response: {}", response.getBody());
            return response.getBody();
            
        } catch (Exception e) {
            logger.error("Error calling order service: {}", e.getMessage());
            throw new RuntimeException("Order service unavailable", e);
        }
    }
}