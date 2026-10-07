package com.bookexchange.model;

import java.time.LocalDateTime;

public class Book {
    private int id;
    private int userId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String condition;
    private String edition;
    private String publisher;
    private Integer publicationYear;
    private String language;
    private String description;
    private String conditionDetails;
    private boolean missingPages;
    private boolean damagedCover;
    private double originalPrice;
    private double price;
    private String saleMode;
    private boolean negotiable;
    private String preferredCategory;
    private String preferredAuthor;
    private String preferredSubject;
    private String college;
    private String branch;
    private String yearSemester;
    private String location;
    private String deliveryOptions;
    private String additionalNotes;
    private String sellingReason;
    private String usageDuration;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String status;
    private LocalDateTime createdAt;

    // Associated owner details for display
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String ownerDepartment;
    private String ownerCourse;

    public Book() {
    }

    public Book(int userId, String title, String author, String isbn, String category,
                String condition, String edition, String description, double price) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.condition = condition;
        this.edition = edition;
        this.description = description;
        this.price = price;
        this.status = "AVAILABLE";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getConditionDetails() { return conditionDetails; }
    public void setConditionDetails(String conditionDetails) { this.conditionDetails = conditionDetails; }
    public boolean isMissingPages() { return missingPages; }
    public void setMissingPages(boolean missingPages) { this.missingPages = missingPages; }
    public boolean isDamagedCover() { return damagedCover; }
    public void setDamagedCover(boolean damagedCover) { this.damagedCover = damagedCover; }
    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSaleMode() { return saleMode; }
    public void setSaleMode(String saleMode) { this.saleMode = saleMode; }
    public boolean isNegotiable() { return negotiable; }
    public void setNegotiable(boolean negotiable) { this.negotiable = negotiable; }
    public String getPreferredCategory() { return preferredCategory; }
    public void setPreferredCategory(String preferredCategory) { this.preferredCategory = preferredCategory; }
    public String getPreferredAuthor() { return preferredAuthor; }
    public void setPreferredAuthor(String preferredAuthor) { this.preferredAuthor = preferredAuthor; }
    public String getPreferredSubject() { return preferredSubject; }
    public void setPreferredSubject(String preferredSubject) { this.preferredSubject = preferredSubject; }
    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public String getYearSemester() { return yearSemester; }
    public void setYearSemester(String yearSemester) { this.yearSemester = yearSemester; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDeliveryOptions() { return deliveryOptions; }
    public void setDeliveryOptions(String deliveryOptions) { this.deliveryOptions = deliveryOptions; }
    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    public String getSellingReason() { return sellingReason; }
    public void setSellingReason(String sellingReason) { this.sellingReason = sellingReason; }
    public String getUsageDuration() { return usageDuration; }
    public void setUsageDuration(String usageDuration) { this.usageDuration = usageDuration; }
    public String getImage1() { return image1; }
    public void setImage1(String image1) { this.image1 = image1; }
    public String getImage2() { return image2; }
    public void setImage2(String image2) { this.image2 = image2; }
    public String getImage3() { return image3; }
    public void setImage3(String image3) { this.image3 = image3; }
    public String getImage4() { return image4; }
    public void setImage4(String image4) { this.image4 = image4; }
    public String getImage5() { return image5; }
    public void setImage5(String image5) { this.image5 = image5; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

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
