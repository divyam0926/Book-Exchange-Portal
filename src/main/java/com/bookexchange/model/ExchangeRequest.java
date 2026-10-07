package com.bookexchange.model;

import java.time.LocalDateTime;

public class ExchangeRequest {
    private int id;
    private int bookId;
    private int requesterId;
    private int ownerId;
    private String status;
    private LocalDateTime requestedAt;
    private String notes;

    // Associated Book Details for UI presentation
    private String bookTitle;
    private String bookAuthor;
    private String bookCategory;
    private double bookPrice;
    private String bookImage;

    // Associated Requester Details
    private String requesterName;
    private String requesterEmail;
    private String requesterPhone;
    private String requesterDepartment;
    private String requesterCourse;

    // Associated Owner Details
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String ownerDepartment;
    private String ownerCourse;

    public ExchangeRequest() {
    }

    public ExchangeRequest(int bookId, int requesterId, int ownerId) {
        this.bookId = bookId;
        this.requesterId = requesterId;
        this.ownerId = ownerId;
        this.status = "PENDING";
        this.notes = "";
    }

    public ExchangeRequest(int bookId, int requesterId, int ownerId, String notes) {
        this.bookId = bookId;
        this.requesterId = requesterId;
        this.ownerId = ownerId;
        this.status = "PENDING";
        this.notes = notes;
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

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public String getBookCategory() { return bookCategory; }
    public void setBookCategory(String bookCategory) { this.bookCategory = bookCategory; }

    public double getBookPrice() { return bookPrice; }
    public void setBookPrice(double bookPrice) { this.bookPrice = bookPrice; }

    public String getBookImage() { return bookImage; }
    public void setBookImage(String bookImage) { this.bookImage = bookImage; }

    public String getRequesterName() { return requesterName; }
    public void setRequesterName(String requesterName) { this.requesterName = requesterName; }

    public String getRequesterEmail() { return requesterEmail; }
    public void setRequesterEmail(String requesterEmail) { this.requesterEmail = requesterEmail; }

    public String getRequesterPhone() { return requesterPhone; }
    public void setRequesterPhone(String requesterPhone) { this.requesterPhone = requesterPhone; }

    public String getRequesterDepartment() { return requesterDepartment; }
    public void setRequesterDepartment(String requesterDepartment) { this.requesterDepartment = requesterDepartment; }

    public String getRequesterCourse() { return requesterCourse; }
    public void setRequesterCourse(String requesterCourse) { this.requesterCourse = requesterCourse; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public String getOwnerPhone() { return ownerPhone; }
    public void setOwnerPhone(String ownerPhone) { this.ownerPhone = ownerPhone; }

    public String getOwnerDepartment() { return ownerDepartment; }
    public void setOwnerDepartment(String ownerDepartment) { this.ownerDepartment = ownerDepartment; }

    public String getOwnerCourse() { return ownerCourse; }
    public void setOwnerCourse(String ownerCourse) { this.ownerCourse = ownerCourse; }
}
