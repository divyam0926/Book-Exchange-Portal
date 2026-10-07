package com.bookexchange.dao;

import com.bookexchange.config.DatabaseConfig;
import com.bookexchange.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password, phone, department, course, role, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getDepartment());
            stmt.setString(6, user.getCourse());
            stmt.setString(7, user.getRole());
            stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && BCrypt.checkpw(password, rs.getString("password"))) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setDepartment(rs.getString("department"));
                user.setCourse(rs.getString("course"));
                user.setRole(rs.getString("role"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setDepartment(rs.getString("department"));
                user.setCourse(rs.getString("course"));
                user.setRole(rs.getString("role"));
                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    user.setCreatedAt(timestamp.toLocalDateTime());
                }
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL, DatabaseConfig.DB_USER, DatabaseConfig.DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName(DatabaseConfig.JDBC_DRIVER);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
