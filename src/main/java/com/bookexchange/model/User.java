package com.bookexchange.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String department;
    private String course;
    private String role;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String fullName, String email, String password, String phone, String department, String course) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.department = department;
        this.course = course;
        this.role = "USER";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
