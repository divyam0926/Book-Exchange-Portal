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
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS exchange_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    requester_id INT NOT NULL,
    owner_id INT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    notes VARCHAR(255) DEFAULT '',
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Seed Demo Users
INSERT INTO users (id, full_name, email, password, phone, department, course, role) VALUES
(1, 'Ananya Nair', 'ananya.nair@college.edu', 'ananya123', '9876501234', 'Computer Science', 'MCA 1st Year', 'USER'),
(4, 'Rahul Verma', 'rahul.verma@college.edu', 'rahul123', '9812345678', 'Computer Applications', 'MCA 2nd Year', 'USER'),
(5, 'Priya Patel', 'priya.patel@college.edu', 'priya123', '9898765432', 'Information Technology', 'B.Tech IT 3rd Year', 'USER'),
(6, 'Divyam Singh', 'divyamsingh0999@gmail.com', 'student123', '9845123456', 'Computer Applications', 'MCA 1st Year', 'USER'),
(7, 'System Administrator', 'admin@bookexchange.com', '$2a$12$TQUh3WkIHiRtFdAVQqU3ieJxcqSAkn8Bt6Afg5ZRZzHyDfng.TNAO', '9876543210', 'Computer Applications', 'MCA', 'ADMIN')
ON DUPLICATE KEY UPDATE full_name=VALUES(full_name);

-- Seed 10 Realistic Books
INSERT INTO books (
    id, user_id, title, author, isbn, category, book_condition, edition, publisher, publication_year,
    language, description, price, sale_mode, preferred_subject, college, branch,
    year_semester, location, delivery_options, status
) VALUES
(1, 4, 'Operating System Concepts', 'Abraham Silberschatz, Peter B. Galvin, Greg Gagne', '978-1118063330', 'Academic', 'Like New', '10th Edition', 'Wiley', 2021, 'English', 'Comprehensive textbook covering processes, threads, CPU scheduling, synchronization, and virtual memory. Essential for 2nd semester OS course.', 0.00, 'Exchange Only', 'Computer Networks or Database Systems', 'Department of Computer Applications', 'MCA', 'Semester 2', 'Central Campus Library', 'Campus Hand-to-hand Meetup', 'AVAILABLE'),
(2, 1, 'Database System Concepts', 'Abraham Silberschatz, Henry F. Korth, S. Sudarshan', '978-0078022159', 'Academic', 'Good', '7th Edition', 'McGraw-Hill Education', 2020, 'English', 'Standard reference book for Relational Models, SQL, Normalization, Query Processing, and Transaction Management. Well-maintained with light pencil annotations.', 350.00, 'Sell or Exchange', 'Web Technologies or Cloud Computing', 'School of Computing Sciences', 'MCA', 'Semester 1', 'North Campus Academic Block', 'Hand Delivery / Campus Meetup', 'AVAILABLE'),
(3, 5, 'Data Structures and Algorithms Made Easy', 'Narasimha Karumanchi', '978-8193245279', 'Engineering', 'Like New', '5th Edition', 'CareerMonk Publications', 2022, 'English', 'Data structure and algorithmic puzzles in Java and C. Highly recommended for campus placements, technical interviews, and DSA lab exam preparation.', 280.00, 'Sell or Exchange', 'System Design or Java Programming', 'Institute of Engineering & Technology', 'B.Tech IT', 'Semester 4', 'Main Canteen Area', 'Campus Pickup', 'AVAILABLE'),
(4, 6, 'Computer Networks: A Top-Down Approach', 'James F. Kurose, Keith W. Ross', '978-0133594140', 'Academic', 'Very Good', '7th Edition', 'Pearson', 2019, 'English', 'Focuses on the Internet architecture, transport layer protocols (TCP/UDP), routing algorithms, and network security. Neat condition with no missing pages.', 0.00, 'Exchange Only', 'Compiler Design or Software Engineering', 'Department of Computer Applications', 'MCA', 'Semester 2', 'Computer Science Block - Lab 3', 'Campus Hand-to-hand Meetup', 'AVAILABLE'),
(5, 4, 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Robert C. Martin', '978-0132350884', 'Novel / Tech', 'Like New', '1st Edition', 'Prentice Hall', 2018, 'English', 'A must-read software engineering classic on writing readable, reusable, and refactorable code. Covers SOLID principles, unit testing, and design patterns.', 420.00, 'Sell or Exchange', 'Design Patterns or Enterprise Java', 'Department of Computer Applications', 'MCA', 'Semester 3', 'Campus Student Centre', 'Campus Pickup / Hand Delivery', 'AVAILABLE'),
(6, 1, 'Core Java Volume I - Fundamentals', 'Cay S. Horstmann', '978-0135115390', 'Academic', 'Good', '11th Edition', 'Prentice Hall', 2021, 'English', 'In-depth coverage of Java SE syntax, OOP, Streams, Lambdas, Exception Handling, and Collections Framework. Used for MCA semester 1 Java course.', 0.00, 'Exchange Only', 'Advanced Web Programming / Spring Boot', 'School of Computing Sciences', 'MCA', 'Semester 1', 'Central Library 2nd Floor', 'Campus Hand-to-hand Meetup', 'AVAILABLE'),
(7, 5, 'GATE CS & IT 2026: Chapterwise Solved Papers', 'Made Easy Editorial Board', '978-9391065447', 'Competitive Exam', 'Good', '2025 Edition', 'Made Easy Publications', 2024, 'English', 'Complete 30+ years topic-wise solved papers with detailed solutions for GATE CS/IT, ISRO, and PSU recruitments. Clean and unmarked.', 450.00, 'Sell or Exchange', 'UGC NET Paper 2 / Higher Math', 'Institute of Engineering & Technology', 'B.Tech IT', 'Final Year', 'Engineering Block A', 'Campus Pickup', 'AVAILABLE'),
(8, 6, 'Discrete Mathematics and Its Applications', 'Kenneth H. Rosen', '978-1259676512', 'Academic', 'Good', '8th Edition', 'McGraw-Hill Education', 2019, 'English', 'Standard textbook for Discrete Math covering propositional logic, set theory, combinatorics, graph theory, and Boolean algebra for MCA students.', 220.00, 'Sell or Exchange', 'Theory of Computation / Automata', 'Department of Computer Applications', 'MCA', 'Semester 1', 'PG Block Foyer', 'Hand Delivery / Campus Meetup', 'AVAILABLE'),
(9, 4, 'Artificial Intelligence: A Modern Approach', 'Stuart Russell, Peter Norvig', '978-0134610993', 'Academic', 'Like New', '4th Edition', 'Pearson', 2021, 'English', 'The definitive guide to modern AI, covering intelligent agents, informed search, adversarial search, probabilistic reasoning, and machine learning foundations.', 520.00, 'Sell or Exchange', 'Deep Learning / NLP', 'Department of Computer Applications', 'MCA', 'Semester 3', 'Campus Technology Park', 'Campus Hand-to-hand Meetup', 'AVAILABLE'),
(10, 1, 'Introduction to the Design and Analysis of Algorithms', 'Anany Levitin', '978-0132316811', 'Academic', 'Good', '3rd Edition', 'Pearson', 2020, 'English', 'Lucid explanation of algorithm design strategies including divide-and-conquer, dynamic programming, greedy approach, and computational complexity analysis.', 0.00, 'Exchange Only', 'Distributed Systems', 'School of Computing Sciences', 'MCA', 'Semester 2', 'Girls Hostel Common Hall', 'Campus Hand-to-hand Meetup', 'AVAILABLE')
ON DUPLICATE KEY UPDATE title=VALUES(title);

SELECT 'Database schema, users, and 10 books initialized successfully.' AS status;
