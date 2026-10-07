CREATE DATABASE IF NOT EXISTS book;
USE book;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(100),
    course VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    isbn VARCHAR(50),
    category VARCHAR(100),
    book_condition VARCHAR(50),
    edition VARCHAR(50),
    publisher VARCHAR(150),
    publication_year INT,
    language VARCHAR(50),
    condition_details VARCHAR(100),
    missing_pages BOOLEAN DEFAULT FALSE,
    damaged_cover BOOLEAN DEFAULT FALSE,
    description TEXT,
    original_price DECIMAL(10,2) DEFAULT 0.0,
    price DOUBLE DEFAULT 0.0,
    negotiable BOOLEAN DEFAULT FALSE,
    sale_mode VARCHAR(30) DEFAULT 'Sell or Exchange',
    preferred_category VARCHAR(100),
    preferred_author VARCHAR(150),
    preferred_subject VARCHAR(150),
    college VARCHAR(150),
    branch VARCHAR(150),
    year_semester VARCHAR(50),
    location VARCHAR(150),
    delivery_options VARCHAR(100),
    additional_notes TEXT,
    selling_reason VARCHAR(255),
    usage_duration VARCHAR(100),
    image1 VARCHAR(255),
    image2 VARCHAR(255),
    image3 VARCHAR(255),
    image4 VARCHAR(255),
    image5 VARCHAR(255),
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS exchange_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    requester_id INT NOT NULL,
    owner_id INT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (requester_id) REFERENCES users(id),
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

SELECT 'Database and tables created successfully.' AS status;
