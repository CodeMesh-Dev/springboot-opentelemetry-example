-- Preloaded test payment data
INSERT INTO payments (id, order_id, amount, status, transaction_id, payment_method, created_at) 
VALUES 
(1, 'ORDER-001', 99.99, 'SUCCESS', 'TXN-1001', 'CREDIT_CARD', '2026-01-14 10:00:00'),
(2, 'ORDER-002', 149.50, 'SUCCESS', 'TXN-1002', 'DEBIT_CARD', '2026-01-14 11:30:00'),
(3, 'ORDER-003', 75.00, 'FAILED', 'TXN-1003', 'CREDIT_CARD', '2026-01-14 12:15:00'),
(4, 'ORDER-004', 299.99, 'SUCCESS', 'TXN-1004', 'PAYPAL', '2026-01-14 14:00:00'),
(5, 'ORDER-005', 45.25, 'FAILED', 'TXN-1005', 'CREDIT_CARD', '2026-01-14 15:45:00');
