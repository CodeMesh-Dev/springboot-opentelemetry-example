package dev.codemesh.opentelemetry.orderservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShippingServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(ShippingServiceClient.class);

    private final RestTemplate restTemplate;
    private final String shippingServiceUrl;

    public ShippingServiceClient(RestTemplate restTemplate,
                                 @Value("${shipping.service.url:http://localhost:9093}") String shippingServiceUrl) {
        this.restTemplate = restTemplate;
        this.shippingServiceUrl = shippingServiceUrl;
    }

    public ShippingResponse createShipment(ShippingRequest request) {
        String url = shippingServiceUrl + "/api/shipping/create";
        
        logger.info("Calling shipping service for order: {}", request.getOrderId());
        
        try {
            ResponseEntity<ShippingResponse> response = restTemplate.postForEntity(
                    url, request, ShippingResponse.class);
            
            logger.info("Shipping service response: {}", response.getBody());
            return response.getBody();
            
        } catch (Exception e) {
            logger.error("Error calling shipping service: {}", e.getMessage());
            throw new RuntimeException("Shipping service unavailable", e);
        }
    }
}
