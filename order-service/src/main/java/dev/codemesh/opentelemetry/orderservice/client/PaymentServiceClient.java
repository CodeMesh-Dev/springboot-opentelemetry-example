package dev.codemesh.opentelemetry.orderservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceClient.class);

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl;

    public PaymentServiceClient(RestTemplate restTemplate,
                                @Value("${payment.service.url:http://localhost:9092}") String paymentServiceUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceUrl = paymentServiceUrl;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        String url = paymentServiceUrl + "/api/payment/process";
        
        logger.info("Calling payment service for order: {}", request.getOrderId());
        
        try {
            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(
                    url, request, PaymentResponse.class);
            
            logger.info("Payment service response: {}", response.getBody());
            return response.getBody();
            
        } catch (Exception e) {
            logger.error("Error calling payment service: {}", e.getMessage());
            throw new RuntimeException("Payment service unavailable", e);
        }
    }
}
