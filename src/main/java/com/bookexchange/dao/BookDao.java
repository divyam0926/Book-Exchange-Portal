package com.bookexchange.dao;

import com.bookexchange.config.DatabaseConfig;
import com.bookexchange.model.Book;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (user_id, title, author, isbn, category, book_condition, edition, publisher, " +
                     "publication_year, language, condition_details, missing_pages, damaged_cover, description, " +
                     "original_price, price, negotiable, sale_mode, preferred_category, preferred_author, preferred_subject, " +
                     "college, branch, year_semester, location, delivery_options, additional_notes, selling_reason, " +
                     "usage_duration, image1, image2, image3, image4, image5, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            stmt.setString(35, book.getStatus() != null ? book.getStatus() : "AVAILABLE");
            stmt.setTimestamp(36, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, category = ?, book_condition = ?, " +
                     "edition = ?, publisher = ?, publication_year = ?, language = ?, condition_details = ?, " +
                     "missing_pages = ?, damaged_cover = ?, description = ?, original_price = ?, price = ?, " +
                     "negotiable = ?, sale_mode = ?, preferred_category = ?, preferred_author = ?, " +
                     "preferred_subject = ?, college = ?, branch = ?, year_semester = ?, location = ?, " +
                     "delivery_options = ?, additional_notes = ?, selling_reason = ?, usage_duration = ?, " +
                     "status = ?" +
                     (book.getImage1() != null ? ", image1 = ?" : "") +
                     (book.getImage2() != null ? ", image2 = ?" : "") +
                     (book.getImage3() != null ? ", image3 = ?" : "") +
                     " WHERE id = ? AND user_id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            int idx = 1;
            stmt.setString(idx++, book.getTitle());
            stmt.setString(idx++, book.getAuthor());
            stmt.setString(idx++, book.getIsbn());
            stmt.setString(idx++, book.getCategory());
            stmt.setString(idx++, book.getCondition());
            stmt.setString(idx++, book.getEdition());
            stmt.setString(idx++, book.getPublisher());
            if (book.getPublicationYear() == null) stmt.setNull(idx++, Types.INTEGER); else stmt.setInt(idx++, book.getPublicationYear());
            stmt.setString(idx++, book.getLanguage());
            stmt.setString(idx++, book.getConditionDetails());
            stmt.setBoolean(idx++, book.isMissingPages());
            stmt.setBoolean(idx++, book.isDamagedCover());
            stmt.setString(idx++, book.getDescription());
            stmt.setDouble(idx++, book.getOriginalPrice());
            stmt.setDouble(idx++, book.getPrice());
            stmt.setBoolean(idx++, book.isNegotiable());
            stmt.setString(idx++, book.getSaleMode());
            stmt.setString(idx++, book.getPreferredCategory());
            stmt.setString(idx++, book.getPreferredAuthor());
            stmt.setString(idx++, book.getPreferredSubject());
            stmt.setString(idx++, book.getCollege());
            stmt.setString(idx++, book.getBranch());
            stmt.setString(idx++, book.getYearSemester());
            stmt.setString(idx++, book.getLocation());
            stmt.setString(idx++, book.getDeliveryOptions());
            stmt.setString(idx++, book.getAdditionalNotes());
            stmt.setString(idx++, book.getSellingReason());
            stmt.setString(idx++, book.getUsageDuration());
            stmt.setString(idx++, book.getStatus());

            if (book.getImage1() != null) stmt.setString(idx++, book.getImage1());
            if (book.getImage2() != null) stmt.setString(idx++, book.getImage2());
            if (book.getImage3() != null) stmt.setString(idx++, book.getImage3());

            stmt.setInt(idx++, book.getId());
            stmt.setInt(idx++, book.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int id) {
        String deleteRequestsSql = "DELETE FROM exchange_requests WHERE book_id = ?";
        String deleteBookSql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD)) {
            Class.forName(DatabaseConfig.JDBC_DRIVER);
            conn.setAutoCommit(false);

            try (PreparedStatement reqStmt = conn.prepareStatement(deleteRequestsSql);
                 PreparedStatement bookStmt = conn.prepareStatement(deleteBookSql)) {

                reqStmt.setInt(1, id);
                reqStmt.executeUpdate();

                bookStmt.setInt(1, id);
                int affected = bookStmt.executeUpdate();

                conn.commit();
                return affected > 0;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int bookId, String status) {
        String sql = "UPDATE books SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setString(1, status);
            stmt.setInt(2, bookId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name AS owner_name, u.email AS owner_email, u.phone AS owner_phone, " +
                     "u.department AS owner_dept, u.course AS owner_course " +
                     "FROM books b LEFT JOIN users u ON b.user_id = u.id ORDER BY b.created_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByUserId(int userId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, u.full_name AS owner_name, u.email AS owner_email, u.phone AS owner_phone, " +
                     "u.department AS owner_dept, u.course AS owner_course " +
                     "FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.user_id = ? ORDER BY b.created_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBooks(String keyword, String category) {
        List<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT b.*, u.full_name AS owner_name, u.email AS owner_email, u.phone AS owner_phone, " +
            "u.department AS owner_dept, u.course AS owner_course " +
            "FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE 1=1 "
        );

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All");

        if (hasKeyword) {
            sql.append("AND (b.title LIKE ? OR b.author LIKE ? OR b.category LIKE ? OR b.isbn LIKE ? OR b.publisher LIKE ? OR b.description LIKE ?) ");
        }
        if (hasCategory) {
            sql.append("AND b.category = ? ");
        }
        sql.append("ORDER BY b.created_at DESC");

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            int idx = 1;
            if (hasKeyword) {
                String like = "%" + keyword.trim() + "%";
                stmt.setString(idx++, like);
                stmt.setString(idx++, like);
                stmt.setString(idx++, like);
                stmt.setString(idx++, like);
                stmt.setString(idx++, like);
                stmt.setString(idx++, like);
            }
            if (hasCategory) {
                stmt.setString(idx++, category.trim());
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBooks(String keyword) {
        return searchBooks(keyword, null);
    }

    public Book getBookById(int id) {
        String sql = "SELECT b.*, u.full_name AS owner_name, u.email AS owner_email, u.phone AS owner_phone, " +
                     "u.department AS owner_dept, u.course AS owner_course " +
                     "FROM books b LEFT JOIN users u ON b.user_id = u.id WHERE b.id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractBookFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setUserId(rs.getInt("user_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setCategory(rs.getString("category"));
        book.setCondition(rs.getString("book_condition"));
        book.setEdition(rs.getString("edition"));
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

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            book.setCreatedAt(timestamp.toLocalDateTime());
        }

        try {
            book.setOwnerName(rs.getString("owner_name"));
            book.setOwnerEmail(rs.getString("owner_email"));
            book.setOwnerPhone(rs.getString("owner_phone"));
            book.setOwnerDepartment(rs.getString("owner_dept"));
            book.setOwnerCourse(rs.getString("owner_course"));
        } catch (SQLException ignored) {
        }

        return book;
    }
}
