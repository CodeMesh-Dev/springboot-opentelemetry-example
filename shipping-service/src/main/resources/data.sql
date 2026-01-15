-- Preloaded test shipment data
INSERT INTO shipments (id, order_id, tracking_number, address, customer_name, status, created_at, estimated_delivery) 
VALUES 
(1, 'ORDER-001', 'TRK-A1B2C3D4', '123 Main St, New York, NY 10001', 'John Doe', 'DELIVERED', '2026-01-10 10:00:00', '2026-01-14 10:00:00'),
(2, 'ORDER-002', 'TRK-E5F6G7H8', '456 Oak Ave, Los Angeles, CA 90001', 'Jane Smith', 'IN_TRANSIT', '2026-01-12 11:30:00', '2026-01-17 11:30:00'),
(3, 'ORDER-004', 'TRK-I9J0K1L2', '789 Pine Rd, Chicago, IL 60601', 'Bob Johnson', 'SHIPPED', '2026-01-14 14:00:00', '2026-01-18 14:00:00');
