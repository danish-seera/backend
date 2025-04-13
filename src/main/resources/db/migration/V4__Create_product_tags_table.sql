CREATE TABLE IF NOT EXISTS product_tags (
    id VARCHAR(36) PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    tag VARCHAR(100) NOT NULL,
    created_at BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
); 