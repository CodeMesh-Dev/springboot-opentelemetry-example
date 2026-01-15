package dev.codemesh.opentelemetry.orderservice.service;

import dev.codemesh.opentelemetry.orderservice.client.*;
import dev.codemesh.opentelemetry.orderservice.model.Order;
import dev.codemesh.opentelemetry.orderservice.model.OrderRequest;
import dev.codemesh.opentelemetry.orderservice.model.OrderResponse;
import dev.codemesh.opentelemetry.orderservice.model.OrderStatus;
import dev.codemesh.opentelemetry.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    private ShippingServiceClient shippingServiceClient;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        // Generate unique order ID
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        logger.info("Creating order: {} with amount: {}", orderId, request.getTotalAmount());

        // Create and save initial order
        Order order = Order.builder()
                .orderId(orderId)
                .totalAmount(request.getTotalAmount())
                .status(OrderStatus.PENDING)
                .shippingAddress(request.getShippingAddress())
                .build();
        
        orderRepository.save(order);

        // Step 1: Process payment
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(orderId)
                .amount(request.getTotalAmount())
                .paymentMethod("CREDIT_CARD")
                .build();

        PaymentResponse paymentResponse;
        try {
            paymentResponse = paymentServiceClient.processPayment(paymentRequest);
        } catch (Exception e) {
            logger.error("Payment service call failed for order: {}", orderId, e);
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(order);
            
            return OrderResponse.builder()
                    .orderId(orderId)
                    .status(OrderStatus.PAYMENT_FAILED.name())
                    .message("Order creation failed - payment service unavailable")
                    .build();
        }

        // Update order with payment transaction ID
        order.setPaymentTransactionId(paymentResponse.getTransactionId());

        // Step 2: Check payment success
        if (!paymentResponse.isSuccess()) {
            logger.warn("Payment failed for order: {}", orderId);
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.save(order);
            
            return OrderResponse.builder()
                    .orderId(orderId)
                    .status(OrderStatus.PAYMENT_FAILED.name())
                    .message("Order creation failed - " + paymentResponse.getMessage())
                    .paymentTransactionId(paymentResponse.getTransactionId())
                    .build();
        }

        logger.info("Payment successful for order: {}, proceeding to shipping", orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        // Step 3: Create shipment (only if payment successful)
        ShippingRequest shippingRequest = ShippingRequest.builder()
                .orderId(orderId)
                .address(request.getShippingAddress())
                .customerName(request.getCustomerName())
                .build();

        ShippingResponse shippingResponse;
        try {
            shippingResponse = shippingServiceClient.createShipment(shippingRequest);
        } catch (Exception e) {
            logger.error("Shipping service call failed for order: {}", orderId, e);
            // Order is confirmed with payment, but shipping failed
            return OrderResponse.builder()
                    .orderId(orderId)
                    .status(OrderStatus.CONFIRMED.name())
                    .message("Order confirmed but shipping creation failed")
                    .paymentTransactionId(paymentResponse.getTransactionId())
                    .build();
        }

        // Update order with shipping tracking number
        order.setShippingTrackingNumber(shippingResponse.getTrackingNumber());
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        logger.info("Order {} completed successfully with tracking: {}", 
                orderId, shippingResponse.getTrackingNumber());

        return OrderResponse.builder()
                .orderId(orderId)
                .status(OrderStatus.SHIPPED.name())
                .message("Order created and shipped successfully")
                .trackingNumber(shippingResponse.getTrackingNumber())
                .paymentTransactionId(paymentResponse.getTransactionId())
                .build();
    }
}
