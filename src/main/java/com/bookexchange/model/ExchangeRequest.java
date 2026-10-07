package com.bookexchange.model;

import java.time.LocalDateTime;

public class ExchangeRequest {
    private int id;
    private int bookId;
    private int requesterId;
    private int ownerId;
    private String status;
    private LocalDateTime requestedAt;

    public ExchangeRequest() {
    }

    public ExchangeRequest(int bookId, int requesterId, int ownerId) {
        this.bookId = bookId;
        this.requesterId = requesterId;
        this.ownerId = ownerId;
        this.status = "PENDING";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getRequesterId() { return requesterId; }
    public void setRequesterId(int requesterId) { this.requesterId = requesterId; }
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }
}
