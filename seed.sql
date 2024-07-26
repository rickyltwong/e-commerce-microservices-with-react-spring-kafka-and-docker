CREATE TABLE IF NOT EXISTS Products (
  product_id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  price DECIMAL(10, 2) NOT NULL,
  category TEXT,
  brand TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  image_path TEXT
);

CREATE TABLE IF NOT EXISTS Inventory (
  inventory_id UUID PRIMARY KEY,
  product_id UUID NOT NULL,
  quantity INT NOT NULL,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE IF NOT EXISTS Orders (
  order_id UUID PRIMARY KEY,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total_amount DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Order_Items (
  order_item_id UUID PRIMARY KEY,
  order_id UUID NOT NULL,
  product_id UUID NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES Orders(order_id),
  FOREIGN KEY (product_id) REFERENCES Products(product_id)
);


-- 
-- Seed data
--

INSERT INTO Products (product_id, name, description, price, category, brand, created_at, updated_at, image_path) VALUES
  (uuid_generate_v4(), 'iPhone 13', 'Latest model iPhone with 5G technology', 999.99, 'Smartphone', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/iphone13.jpg'),
  (uuid_generate_v4(), 'iPhone 12', 'Previous generation iPhone', 799.99, 'Smartphone', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/iphone12.jpg'),
  (uuid_generate_v4(), 'iPhone SE', 'Compact and affordable iPhone', 499.99, 'Smartphone', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/iphonese.jpg'),
  (uuid_generate_v4(), 'iPad Pro', 'High performance tablet', 1099.99, 'Tablet', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/ipadpro.jpg'),
  (uuid_generate_v4(), 'iPad', 'Lightweight and powerful tablet', 599.99, 'Tablet', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/ipad.jpg'),
  (uuid_generate_v4(), 'MacBook', 'Lightweight and powerful laptop', 999.99, 'Laptop', 'Apple', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '/app/images/macbook.jpg');

INSERT INTO Inventory (inventory_id, product_id, quantity, last_updated) VALUES
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'iPhone 13'), 100, CURRENT_TIMESTAMP),
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'iPhone 12'), 150, CURRENT_TIMESTAMP),
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'iPhone SE'), 200, CURRENT_TIMESTAMP),
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'iPad Pro'), 75, CURRENT_TIMESTAMP),
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'iPad'), 120, CURRENT_TIMESTAMP),
  (uuid_generate_v4(), (SELECT product_id FROM Products WHERE name = 'MacBook'), 80, CURRENT_TIMESTAMP);