package dev.codemesh.opentelemetry.payment.controller;

import dev.codemesh.opentelemetry.payment.model.PaymentRequest;
import dev.codemesh.opentelemetry.payment.model.PaymentResponse;
import dev.codemesh.opentelemetry.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentRestController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        logger.info("Received payment request for order: {}", request.getOrderId());
        
        PaymentResponse response = paymentService.processPayment(request);
        
        logger.info("Payment response: {}", response.isSuccess() ? "SUCCESS" : "FAILED");
        
        return ResponseEntity.ok(response);
    }
}
