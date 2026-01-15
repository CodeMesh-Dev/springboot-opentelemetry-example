-- Preloaded test order data
INSERT INTO orders (id, order_id, total_amount, status, payment_transaction_id, shipping_tracking_number, shipping_address, created_at)
VALUES
(1, 'ORD-TEST001', 99.99, 'SHIPPED', 'TXN-1001', 'TRK-A1B2C3D4', '123 Main St, New York, NY 10001', '2026-01-10 10:00:00'),
(2, 'ORD-TEST002', 149.50, 'SHIPPED', 'TXN-1002', 'TRK-E5F6G7H8', '456 Oak Ave, Los Angeles, CA 90001', '2026-01-12 11:30:00'),
(3, 'ORD-TEST003', 75.00, 'PAYMENT_FAILED', 'TXN-1003', NULL, '789 Elm St, Houston, TX 77001', '2026-01-14 12:15:00'),
(4, 'ORD-TEST004', 299.99, 'SHIPPED', 'TXN-1004', 'TRK-I9J0K1L2', '321 Pine Rd, Chicago, IL 60601', '2026-01-14 14:00:00');
