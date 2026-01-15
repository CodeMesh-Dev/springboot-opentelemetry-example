package dev.codemesh.opentelemetry.payment.service;

import dev.codemesh.opentelemetry.payment.model.Payment;
import dev.codemesh.opentelemetry.payment.model.PaymentRequest;
import dev.codemesh.opentelemetry.payment.model.PaymentResponse;
import dev.codemesh.opentelemetry.payment.model.PaymentStatus;
import dev.codemesh.opentelemetry.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    
    private final PaymentRepository paymentRepository;
    private final Random random;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.random = new Random();
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        logger.info("Processing payment for order: {}, amount: {}", request.getOrderId(), request.getAmount());

        // Simulate 50% success rate
        boolean paymentSuccessful = random.nextBoolean();
        
        String transactionId = UUID.randomUUID().toString();
        PaymentStatus status = paymentSuccessful ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        // Create and save payment record
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .status(status)
                .transactionId(transactionId)
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CREDIT_CARD")
                .build();

        paymentRepository.save(payment);

        String message = paymentSuccessful 
                ? "Payment processed successfully" 
                : "Payment processing failed - insufficient funds";

        logger.info("Payment {} for order: {} with transaction ID: {}", 
                status, request.getOrderId(), transactionId);

        return PaymentResponse.builder()
                .transactionId(transactionId)
                .success(paymentSuccessful)
                .message(message)
                .orderId(request.getOrderId())
                .build();
    }
}
