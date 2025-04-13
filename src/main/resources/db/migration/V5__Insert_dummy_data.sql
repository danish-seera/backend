-- Insert products
INSERT INTO products (id, title, description, price, available, category, created_at) VALUES
('p1', 'Nike Air Max', 'Comfortable running shoes with air cushioning', 129.99, true, 'Footwear', 1713000000000),
('p2', 'Adidas T-Shirt', 'Classic cotton t-shirt with logo', 29.99, true, 'Clothing', 1713000000000),
('p3', 'Apple Watch', 'Smartwatch with fitness tracking', 399.99, true, 'Electronics', 1713000000000),
('p4', 'Levi\'s Jeans', 'Classic fit denim jeans', 59.99, true, 'Clothing', 1713000000000),
('p5', 'Sony Headphones', 'Noise cancelling wireless headphones', 199.99, true, 'Electronics', 1713000000000);

-- Insert product images
INSERT INTO product_images (id, product_id, image_url, is_primary, created_at) VALUES
('pi1', 'p1', 'https://example.com/nike-air-max-1.jpg', true, 1713000000000),
('pi2', 'p1', 'https://example.com/nike-air-max-2.jpg', false, 1713000000000),
('pi3', 'p2', 'https://example.com/adidas-tshirt-1.jpg', true, 1713000000000),
('pi4', 'p3', 'https://example.com/apple-watch-1.jpg', true, 1713000000000),
('pi5', 'p4', 'https://example.com/levis-jeans-1.jpg', true, 1713000000000),
('pi6', 'p5', 'https://example.com/sony-headphones-1.jpg', true, 1713000000000);

-- Insert product variants
INSERT INTO product_variants (id, product_id, name, price, stock) VALUES
('pv1', 'p1', 'Size 8', 129.99, 10),
('pv2', 'p1', 'Size 9', 129.99, 15),
('pv3', 'p1', 'Size 10', 129.99, 8),
('pv4', 'p2', 'Small', 29.99, 20),
('pv5', 'p2', 'Medium', 29.99, 25),
('pv6', 'p2', 'Large', 29.99, 15),
('pv7', 'p3', '44mm', 399.99, 5),
('pv8', 'p3', '40mm', 399.99, 7),
('pv9', 'p4', '32x30', 59.99, 12),
('pv10', 'p4', '34x30', 59.99, 10),
('pv11', 'p5', 'Black', 199.99, 8),
('pv12', 'p5', 'White', 199.99, 6);

-- Insert product tags
INSERT INTO product_tags (id, product_id, tag, created_at) VALUES
('pt1', 'p1', 'running', 1713000000000),
('pt2', 'p1', 'sports', 1713000000000),
('pt3', 'p1', 'shoes', 1713000000000),
('pt4', 'p2', 'casual', 1713000000000),
('pt5', 'p2', 'summer', 1713000000000),
('pt6', 'p3', 'smartwatch', 1713000000000),
('pt7', 'p3', 'fitness', 1713000000000),
('pt8', 'p4', 'denim', 1713000000000),
('pt9', 'p4', 'casual', 1713000000000),
('pt10', 'p5', 'audio', 1713000000000),
('pt11', 'p5', 'wireless', 1713000000000); 