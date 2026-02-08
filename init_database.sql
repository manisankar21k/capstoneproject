-- ============================================================
-- INSURANCE POLICY MANAGEMENT SYSTEM - DATABASE SETUP
-- ============================================================
-- Run this script in MySQL Workbench to initialize your database
-- 
-- DATABASE STRUCTURE:
-- - customers: Individual customers (John Doe, Jane Smith, etc.)
-- - policies: Insurance policies (Life, Health, Vehicle, Home, Travel)
-- - customer_policies: Join table linking customers to policies
--   * AUTO-generated alphanumeric IDs (CP1001, CS2001, etc.)
--   * Enables multiple policies per customer
-- - payments: Premium payment records for customer policies
--   * Modes: ONLINE, OFFLINE, CHECK, DD
--   * Statuses: PENDING, COMPLETED
-- - claims: Insurance claims submitted against customer policies
--   * Statuses: PENDING, PROCESSING, APPROVED, REJECTED
--   * Includes claim amount and description
--
-- FRONTEND/BACKEND INTEGRATION:
-- - Angular frontend (port 4200) connects to Spring Boot API (port 8080)
-- - Customer/Policy dropdowns filter to show relevant options
-- - Backend auto-generates customer_policy_id on assignment
-- - All relationships support eager-loading for JSON serialization
--
-- Instructions: 
-- 1. Open MySQL Workbench
-- 2. Go to File > Open SQL Script
-- 3. Select this init_database.sql file
-- 4. Click Execute (or press Ctrl+Shift+Enter)
-- ============================================================

-- ============================================================
-- 1. CREATE DATABASE
-- ============================================================
DROP DATABASE IF EXISTS insurance_db;
CREATE DATABASE IF NOT EXISTS insurance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE insurance_db;

-- ============================================================
-- 2. CREATE USERS TABLE (Authentication)
-- ============================================================
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Default Admin User
INSERT INTO users (username, password, email, full_name) VALUES 
('admin', 'admin123', 'admin@insurance.com', 'Administrator');

-- ============================================================
-- 3. CREATE CUSTOMERS TABLE (Core Data)
-- ============================================================
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    address VARCHAR(255),
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_customer_email (email),
    INDEX idx_customer_name (first_name, last_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Customers
INSERT INTO customers (first_name, last_name, email, phone, address, date_of_birth) VALUES 
('John', 'Doe', 'john.doe@example.com', '9876543210', '123 Main Street', '1990-05-15'),
('Jane', 'Smith', 'jane.smith@example.com', '9876543211', '456 Oak Avenue', '1985-08-22'),
('Robert', 'Johnson', 'robert.johnson@example.com', '9876543212', '789 Pine Road', '1992-03-10'),
('Emily', 'Williams', 'emily.williams@example.com', '9876543213', '321 Elm Street', '1988-11-30'),
('Michael', 'Brown', 'michael.brown@example.com', '9876543214', '654 Maple Drive', '1995-07-18');

-- ============================================================
-- 4. CREATE POLICIES TABLE
-- ============================================================
CREATE TABLE policies (
    policy_id INT PRIMARY KEY AUTO_INCREMENT,
    policy_name VARCHAR(100) NOT NULL,
    policy_type VARCHAR(50) NOT NULL,
    premium_amount DECIMAL(10, 2) NOT NULL,
    duration_months INT,
    coverage_amount DECIMAL(12, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_policy_type (policy_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Policies
INSERT INTO policies (policy_name, policy_type, premium_amount, duration_months, coverage_amount) VALUES 
('Term Life Insurance', 'Life', 5000.00, 12, 1000000.00),
('Health Insurance Plus', 'Health', 12000.00, 12, 500000.00),
('Vehicle Comprehensive', 'Vehicle', 8500.00, 12, 2000000.00),
('Home Safety Plus', 'Home', 15000.00, 12, 5000000.00),
('Travel Guard', 'Travel', 2500.00, 1, 1000000.00);

-- ============================================================
-- 5. CREATE CUSTOMER_POLICIES TABLE (Join Table)
-- ============================================================
CREATE TABLE customer_policies (
    customer_policy_id VARCHAR(50) PRIMARY KEY,
    customer_id INT NOT NULL,
    policy_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    policy_status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id) ON DELETE CASCADE,
    INDEX idx_customer_policies_customer (customer_id),
    INDEX idx_customer_policies_policy (policy_id),
    INDEX idx_customer_policies_status (policy_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Customer-Policy Mappings (Multiple policies per customer for better testing)
INSERT INTO customer_policies (customer_policy_id, customer_id, policy_id, start_date, end_date, policy_status) VALUES 
('CP1001', 1, 1, '2026-02-07', '2027-02-07', 'ACTIVE'),
('CP1002', 1, 3, '2026-02-07', '2027-02-07', 'ACTIVE'),
('CP1003', 1, 5, '2026-02-01', '2026-03-01', 'ACTIVE'),
('CS2001', 2, 2, '2026-02-07', '2027-02-07', 'ACTIVE'),
('CS2002', 2, 4, '2026-01-15', '2027-01-15', 'ACTIVE'),
('RJ3001', 3, 1, '2025-12-01', '2026-12-01', 'ACTIVE'),
('RJ3002', 3, 4, '2026-02-07', '2027-02-07', 'ACTIVE'),
('EW4001', 4, 2, '2026-01-01', '2027-01-01', 'ACTIVE'),
('EW4002', 4, 5, '2026-02-07', '2026-03-07', 'ACTIVE'),
('MB5001', 5, 1, '2026-02-07', '2027-02-07', 'ACTIVE'),
('MB5002', 5, 3, '2025-11-01', '2026-11-01', 'ACTIVE');

-- ============================================================
-- 6. CREATE PAYMENTS TABLE
-- ============================================================
CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_policy_id VARCHAR(50),
    payment_date DATE DEFAULT '2026-02-07',
    amount DECIMAL(10, 2) NOT NULL,
    payment_mode VARCHAR(50),
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_policy_id) REFERENCES customer_policies(customer_policy_id) ON DELETE SET NULL,
    INDEX idx_payments_status (payment_status),
    INDEX idx_payments_customer_policy (customer_policy_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Payments (Multiple per customer to test filtering and history)
INSERT INTO payments (customer_policy_id, amount, payment_mode, payment_status) VALUES 
('CP1001', 5000.00, 'ONLINE', 'COMPLETED'),
('CP1002', 8500.00, 'OFFLINE', 'COMPLETED'),
('CP1003', 2500.00, 'CHECK', 'COMPLETED'),
('CS2001', 12000.00, 'ONLINE', 'COMPLETED'),
('CS2001', 12000.00, 'ONLINE', 'PENDING'),
('CS2002', 15000.00, 'DD', 'COMPLETED'),
('RJ3001', 5000.00, 'ONLINE', 'COMPLETED'),
('RJ3002', 15000.00, 'OFFLINE', 'PENDING'),
('EW4001', 12000.00, 'CHECK', 'COMPLETED'),
('EW4002', 2500.00, 'ONLINE', 'COMPLETED'),
('MB5001', 5000.00, 'DD', 'COMPLETED'),
('MB5002', 8500.00, 'ONLINE', 'PENDING');

-- ============================================================
-- 7. CREATE CLAIMS TABLE
-- ============================================================
CREATE TABLE claims (
    claim_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_policy_id VARCHAR(50),
    claim_date DATE DEFAULT '2026-02-07',
    claim_amount DECIMAL(12, 2) NOT NULL,
    claim_status VARCHAR(20) DEFAULT 'PENDING',
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_policy_id) REFERENCES customer_policies(customer_policy_id) ON DELETE SET NULL,
    INDEX idx_claims_status (claim_status),
    INDEX idx_claims_customer_policy (customer_policy_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Claims (Multiple per customer to test filtering and history)
INSERT INTO claims (customer_policy_id, claim_amount, claim_status, description) VALUES 
('CP1001', 50000.00, 'APPROVED', 'Medical emergency claim for hospitalization'),
('CP1001', 25000.00, 'PENDING', 'Follow-up treatment claim'),
('CP1002', 150000.00, 'APPROVED', 'Vehicle accident claim - partial coverage'),
('CP1003', 5000.00, 'PROCESSING', 'Travel insurance claim for flight cancellation'),
('CS2001', 100000.00, 'APPROVED', 'Health insurance claim for surgery'),
('CS2001', 50000.00, 'PENDING', 'Dental treatment claim'),
('CS2002', 75000.00, 'REJECTED', 'Pre-existing condition claim'),
('RJ3001', 80000.00, 'PROCESSING', 'Life insurance death benefit claim'),
('RJ3002', 200000.00, 'PENDING', 'House damage claim - under investigation'),
('EW4001', 120000.00, 'APPROVED', 'Cancer treatment claim'),
('EW4002', 10000.00, 'REJECTED', 'Non-covered expenses claim'),
('MB5001', 60000.00, 'APPROVED', 'Critical illness claim'),
('MB5002', 180000.00, 'PENDING', 'Auto accident comprehensive claim');

-- ============================================================
-- 8. VERIFICATION QUERIES
-- ============================================================
SELECT '✓ Database Setup Complete!' as Status;
SELECT COUNT(*) as Total_Customers FROM customers;
SELECT COUNT(*) as Total_Policies FROM policies;
SELECT COUNT(*) as Total_Customer_Policies FROM customer_policies;
SELECT COUNT(*) as Total_Payments FROM payments;
SELECT COUNT(*) as Total_Claims FROM claims;

-- ============================================================
-- 9. SAMPLE TEST DATA OVERVIEW
-- ============================================================
-- Customers with their policies:
SELECT 
    CONCAT(c.first_name, ' ', c.last_name) as Customer,
    COUNT(cp.customer_policy_id) as Policy_Count,
    GROUP_CONCAT(cp.customer_policy_id ORDER BY cp.customer_policy_id SEPARATOR ', ') as Customer_Policy_IDs
FROM customers c
LEFT JOIN customer_policies cp ON c.customer_id = cp.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
ORDER BY c.customer_id;

-- Payment summary by customer:
SELECT 
    CONCAT(c.first_name, ' ', c.last_name) as Customer,
    COUNT(p.payment_id) as Total_Payments,
    SUM(p.amount) as Total_Amount,
    GROUP_CONCAT(DISTINCT p.payment_status ORDER BY p.payment_status SEPARATOR ', ') as Payment_Statuses
FROM customers c
LEFT JOIN customer_policies cp ON c.customer_id = cp.customer_id
LEFT JOIN payments p ON cp.customer_policy_id = p.customer_policy_id
GROUP BY c.customer_id, c.first_name, c.last_name
ORDER BY c.customer_id;

-- Claims summary by customer:
SELECT 
    CONCAT(c.first_name, ' ', c.last_name) as Customer,
    COUNT(cl.claim_id) as Total_Claims,
    SUM(cl.claim_amount) as Total_Claim_Amount,
    GROUP_CONCAT(DISTINCT cl.claim_status ORDER BY cl.claim_status SEPARATOR ', ') as Claim_Statuses
FROM customers c
LEFT JOIN customer_policies cp ON c.customer_id = cp.customer_id
LEFT JOIN claims cl ON cp.customer_policy_id = cl.customer_policy_id
GROUP BY c.customer_id, c.first_name, c.last_name
ORDER BY c.customer_id;
