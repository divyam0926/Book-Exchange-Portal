# Book Exchange Portal

A Java-based web application for exchanging and managing books among students.

## Features

- User registration and login
- Secure session-based authentication
- Add books to the exchange platform
- Search books by title, author, category, or ISBN
- Dashboard listing available books
- MySQL database integration
- JSP + Servlet web architecture

## Project Structure

- `src/main/java/com/bookexchange/model` – application data models
- `src/main/java/com/bookexchange/dao` – database access layer
- `src/main/java/com/bookexchange/servlet` – servlets for web requests
- `src/main/webapp/WEB-INF/views` – JSP pages
- `src/main/webapp/assets` – CSS and JavaScript assets

## Database Setup

1. Create a MySQL database named `book`.
2. Run the SQL below:

```sql
CREATE DATABASE IF NOT EXISTS book;
USE book;

CREATE TABLE users (
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

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    isbn VARCHAR(50),
    category VARCHAR(100),
    book_condition VARCHAR(50),
    edition VARCHAR(50),
    description TEXT,
    price DOUBLE DEFAULT 0.0,
    status VARCHAR(30) DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE exchange_requests (
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
```

## Run locally

1. Start MySQL and set the credentials in `DatabaseConfig.java` if needed.
2. Build the project:

```bash
mvn clean package
```

3. Deploy the generated WAR file to Apache Tomcat.

## Note

This implementation provides the foundational Java web app structure and modules described in your project synopsis, including registration, login, dashboard, and book management flows.
