package com.bookexchange.dao;

import com.bookexchange.config.DatabaseConfig;
import com.bookexchange.model.Book;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (user_id, title, author, isbn, category, book_condition, edition, publisher, publication_year, language, condition_details, missing_pages, damaged_cover, description, original_price, price, negotiable, sale_mode, preferred_category, preferred_author, preferred_subject, college, branch, year_semester, location, delivery_options, additional_notes, selling_reason, usage_duration, image1, image2, image3, image4, image5, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, book.getUserId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, book.getCategory());
            stmt.setString(6, book.getCondition());
            stmt.setString(7, book.getEdition());
            stmt.setString(8, book.getPublisher());
            if (book.getPublicationYear() == null) stmt.setNull(9, Types.INTEGER); else stmt.setInt(9, book.getPublicationYear());
            stmt.setString(10, book.getLanguage());
            stmt.setString(11, book.getConditionDetails());
            stmt.setBoolean(12, book.isMissingPages());
            stmt.setBoolean(13, book.isDamagedCover());
            stmt.setString(14, book.getDescription());
            stmt.setDouble(15, book.getOriginalPrice());
            stmt.setDouble(16, book.getPrice());
            stmt.setBoolean(17, book.isNegotiable());
            stmt.setString(18, book.getSaleMode());
            stmt.setString(19, book.getPreferredCategory());
            stmt.setString(20, book.getPreferredAuthor());
            stmt.setString(21, book.getPreferredSubject());
            stmt.setString(22, book.getCollege());
            stmt.setString(23, book.getBranch());
            stmt.setString(24, book.getYearSemester());
            stmt.setString(25, book.getLocation());
            stmt.setString(26, book.getDeliveryOptions());
            stmt.setString(27, book.getAdditionalNotes());
            stmt.setString(28, book.getSellingReason());
            stmt.setString(29, book.getUsageDuration());
            stmt.setString(30, book.getImage1());
            stmt.setString(31, book.getImage2());
            stmt.setString(32, book.getImage3());
            stmt.setString(33, book.getImage4());
            stmt.setString(34, book.getImage5());
            stmt.setString(35, book.getStatus());
            stmt.setTimestamp(36, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY created_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setUserId(rs.getInt("user_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setCondition(rs.getString("book_condition"));
                mapExtendedFields(book, rs);
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    book.setCreatedAt(timestamp.toLocalDateTime());
                }
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR isbn LIKE ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            String likeKeyword = "%" + keyword + "%";
            stmt.setString(1, likeKeyword);
            stmt.setString(2, likeKeyword);
            stmt.setString(3, likeKeyword);
            stmt.setString(4, likeKeyword);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setUserId(rs.getInt("user_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setCondition(rs.getString("book_condition"));
                mapExtendedFields(book, rs);
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    book.setCreatedAt(timestamp.toLocalDateTime());
                }
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setUserId(rs.getInt("user_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setCondition(rs.getString("book_condition"));
                mapExtendedFields(book, rs);
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    book.setCreatedAt(timestamp.toLocalDateTime());
                }
                return book;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mapExtendedFields(Book book, ResultSet rs) throws SQLException {
        book.setPublisher(rs.getString("publisher"));
        int publicationYear = rs.getInt("publication_year");
        book.setPublicationYear(rs.wasNull() ? null : publicationYear);
        book.setLanguage(rs.getString("language"));
        book.setConditionDetails(rs.getString("condition_details"));
        book.setMissingPages(rs.getBoolean("missing_pages"));
        book.setDamagedCover(rs.getBoolean("damaged_cover"));
        book.setDescription(rs.getString("description"));
        book.setOriginalPrice(rs.getDouble("original_price"));
        book.setPrice(rs.getDouble("price"));
        book.setNegotiable(rs.getBoolean("negotiable"));
        book.setSaleMode(rs.getString("sale_mode"));
        book.setPreferredCategory(rs.getString("preferred_category"));
        book.setPreferredAuthor(rs.getString("preferred_author"));
        book.setPreferredSubject(rs.getString("preferred_subject"));
        book.setCollege(rs.getString("college"));
        book.setBranch(rs.getString("branch"));
        book.setYearSemester(rs.getString("year_semester"));
        book.setLocation(rs.getString("location"));
        book.setDeliveryOptions(rs.getString("delivery_options"));
        book.setAdditionalNotes(rs.getString("additional_notes"));
        book.setSellingReason(rs.getString("selling_reason"));
        book.setUsageDuration(rs.getString("usage_duration"));
        book.setImage1(rs.getString("image1"));
        book.setImage2(rs.getString("image2"));
        book.setImage3(rs.getString("image3"));
        book.setImage4(rs.getString("image4"));
        book.setImage5(rs.getString("image5"));
        book.setStatus(rs.getString("status"));
    }
}
