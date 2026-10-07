package com.bookexchange.dao;

import com.bookexchange.config.DatabaseConfig;
import com.bookexchange.model.ExchangeRequest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRequestDao {

    public boolean createRequest(ExchangeRequest exchangeRequest) {
        String sql = "INSERT INTO exchange_requests (book_id, requester_id, owner_id, status, requested_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, exchangeRequest.getBookId());
            stmt.setInt(2, exchangeRequest.getRequesterId());
            stmt.setInt(3, exchangeRequest.getOwnerId());
            stmt.setString(4, exchangeRequest.getStatus());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ExchangeRequest> getRequestsForUser(int userId) {
        List<ExchangeRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM exchange_requests WHERE owner_id = ? OR requester_id = ? ORDER BY requested_at DESC";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ExchangeRequest request = new ExchangeRequest();
                request.setId(rs.getInt("id"));
                request.setBookId(rs.getInt("book_id"));
                request.setRequesterId(rs.getInt("requester_id"));
                request.setOwnerId(rs.getInt("owner_id"));
                request.setStatus(rs.getString("status"));
                Timestamp timestamp = rs.getTimestamp("requested_at");
                if (timestamp != null) {
                    request.setRequestedAt(timestamp.toLocalDateTime());
                }
                requests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }
}
