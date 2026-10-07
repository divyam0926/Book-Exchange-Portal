package com.bookexchange.dao;

import com.bookexchange.config.DatabaseConfig;
import com.bookexchange.model.ExchangeRequest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRequestDao {

    public boolean createRequest(ExchangeRequest exchangeRequest) {
        String sql = "INSERT INTO exchange_requests (book_id, requester_id, owner_id, status, notes, requested_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, exchangeRequest.getBookId());
            stmt.setInt(2, exchangeRequest.getRequesterId());
            stmt.setInt(3, exchangeRequest.getOwnerId());
            stmt.setString(4, "PENDING");
            stmt.setString(5, exchangeRequest.getNotes() != null ? exchangeRequest.getNotes() : "");
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasPendingRequest(int bookId, int requesterId) {
        String sql = "SELECT id FROM exchange_requests WHERE book_id = ? AND requester_id = ? AND status = 'PENDING'";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, bookId);
            stmt.setInt(2, requesterId);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getPendingBookIdsForUser(int requesterId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT book_id FROM exchange_requests WHERE requester_id = ? AND status = 'PENDING'";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, requesterId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("book_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getPendingIncomingCount(int ownerId) {
        String sql = "SELECT COUNT(*) FROM exchange_requests WHERE owner_id = ? AND status = 'PENDING'";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, ownerId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ExchangeRequest> getIncomingRequests(int ownerId) {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT er.*, b.title AS book_title, b.author AS book_author, b.category AS book_category, " +
                     "b.price AS book_price, b.image1 AS book_image, " +
                     "u.full_name AS requester_name, u.email AS requester_email, u.phone AS requester_phone, " +
                     "u.department AS requester_dept, u.course AS requester_course " +
                     "FROM exchange_requests er " +
                     "JOIN books b ON er.book_id = b.id " +
                     "JOIN users u ON er.requester_id = u.id " +
                     "WHERE er.owner_id = ? ORDER BY er.requested_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, ownerId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExchangeRequest req = new ExchangeRequest();
                mapBasicFields(req, rs);
                req.setBookTitle(rs.getString("book_title"));
                req.setBookAuthor(rs.getString("book_author"));
                req.setBookCategory(rs.getString("book_category"));
                req.setBookPrice(rs.getDouble("book_price"));
                req.setBookImage(rs.getString("book_image"));
                req.setRequesterName(rs.getString("requester_name"));
                req.setRequesterEmail(rs.getString("requester_email"));
                req.setRequesterPhone(rs.getString("requester_phone"));
                req.setRequesterDepartment(rs.getString("requester_dept"));
                req.setRequesterCourse(rs.getString("requester_course"));
                requests.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ExchangeRequest> getOutgoingRequests(int requesterId) {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT er.*, b.title AS book_title, b.author AS book_author, b.category AS book_category, " +
                     "b.price AS book_price, b.image1 AS book_image, " +
                     "u.full_name AS owner_name, u.email AS owner_email, u.phone AS owner_phone, " +
                     "u.department AS owner_dept, u.course AS owner_course " +
                     "FROM exchange_requests er " +
                     "JOIN books b ON er.book_id = b.id " +
                     "JOIN users u ON er.owner_id = u.id " +
                     "WHERE er.requester_id = ? ORDER BY er.requested_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, requesterId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExchangeRequest req = new ExchangeRequest();
                mapBasicFields(req, rs);
                req.setBookTitle(rs.getString("book_title"));
                req.setBookAuthor(rs.getString("book_author"));
                req.setBookCategory(rs.getString("book_category"));
                req.setBookPrice(rs.getDouble("book_price"));
                req.setBookImage(rs.getString("book_image"));
                req.setOwnerName(rs.getString("owner_name"));
                req.setOwnerEmail(rs.getString("owner_email"));
                req.setOwnerPhone(rs.getString("owner_phone"));
                req.setOwnerDepartment(rs.getString("owner_dept"));
                req.setOwnerCourse(rs.getString("owner_course"));
                requests.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ExchangeRequest> getAllRequests() {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT er.*, b.title AS book_title, " +
                     "req.full_name AS requester_name, req.email AS requester_email, " +
                     "own.full_name AS owner_name, own.email AS owner_email " +
                     "FROM exchange_requests er " +
                     "JOIN books b ON er.book_id = b.id " +
                     "JOIN users req ON er.requester_id = req.id " +
                     "JOIN users own ON er.owner_id = own.id " +
                     "ORDER BY er.requested_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            while (rs.next()) {
                ExchangeRequest req = new ExchangeRequest();
                mapBasicFields(req, rs);
                req.setBookTitle(rs.getString("book_title"));
                req.setRequesterName(rs.getString("requester_name"));
                req.setRequesterEmail(rs.getString("requester_email"));
                req.setOwnerName(rs.getString("owner_name"));
                req.setOwnerEmail(rs.getString("owner_email"));
                requests.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ExchangeRequest getRequestById(int id) {
        String sql = "SELECT * FROM exchange_requests WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ExchangeRequest req = new ExchangeRequest();
                mapBasicFields(req, rs);
                return req;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean acceptRequest(int requestId, int ownerId) {
        String fetchSql = "SELECT book_id FROM exchange_requests WHERE id = ? AND owner_id = ?";
        String updateReqSql = "UPDATE exchange_requests SET status = 'ACCEPTED' WHERE id = ? AND owner_id = ?";
        String updateBookSql = "UPDATE books SET status = 'EXCHANGED' WHERE id = ?";
        String rejectOthersSql = "UPDATE exchange_requests SET status = 'REJECTED' WHERE book_id = ? AND id != ? AND status = 'PENDING'";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD)) {
            Class.forName(DatabaseConfig.JDBC_DRIVER);
            conn.setAutoCommit(false);

            int bookId = -1;
            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchSql)) {
                fetchStmt.setInt(1, requestId);
                fetchStmt.setInt(2, ownerId);
                ResultSet rs = fetchStmt.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                }
            }

            if (bookId == -1) {
                conn.rollback();
                return false;
            }

            try (PreparedStatement stmt1 = conn.prepareStatement(updateReqSql);
                 PreparedStatement stmt2 = conn.prepareStatement(updateBookSql);
                 PreparedStatement stmt3 = conn.prepareStatement(rejectOthersSql)) {

                stmt1.setInt(1, requestId);
                stmt1.setInt(2, ownerId);
                stmt1.executeUpdate();

                stmt2.setInt(1, bookId);
                stmt2.executeUpdate();

                stmt3.setInt(1, bookId);
                stmt3.setInt(2, requestId);
                stmt3.executeUpdate();

                conn.commit();
                return true;
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

    public boolean rejectRequest(int requestId, int ownerId) {
        String sql = "UPDATE exchange_requests SET status = 'REJECTED' WHERE id = ? AND owner_id = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, requestId);
            stmt.setInt(2, ownerId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelRequest(int requestId, int requesterId) {
        String sql = "UPDATE exchange_requests SET status = 'CANCELLED' WHERE id = ? AND requester_id = ? AND status = 'PENDING'";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, requestId);
            stmt.setInt(2, requesterId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mapBasicFields(ExchangeRequest req, ResultSet rs) throws SQLException {
        req.setId(rs.getInt("id"));
        req.setBookId(rs.getInt("book_id"));
        req.setRequesterId(rs.getInt("requester_id"));
        req.setOwnerId(rs.getInt("owner_id"));
        req.setStatus(rs.getString("status"));
        try {
            req.setNotes(rs.getString("notes"));
        } catch (SQLException ignored) {
        }
        Timestamp timestamp = rs.getTimestamp("requested_at");
        if (timestamp != null) {
            req.setRequestedAt(timestamp.toLocalDateTime());
        }
    }
}
