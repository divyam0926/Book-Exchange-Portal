<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Book | Book Exchange Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=20261007b">
</head>
<body>
    <nav class="topbar">
        <div class="logo">
            <a href="dashboard">
                <span>📚</span>
                <span>Book Exchange Portal</span>
            </a>
            <span class="logo-badge">MCA</span>
        </div>
        <div class="nav-links">
            <a href="dashboard">Home</a>
            <a href="add-book">+ Add Book</a>
            <a href="my-books" class="active">My Books</a>
            <a href="exchange-requests">
                Exchange Requests
                <c:if test="${sessionScope.pendingIncomingCount > 0}">
                    <span class="nav-badge">${sessionScope.pendingIncomingCount}</span>
                </c:if>
            </a>
            <a href="profile">Profile</a>
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <a href="admin" class="admin-nav-link">Admin Panel</a>
            </c:if>
            <div class="nav-user">
                <span class="user-avatar">
                    ${not empty sessionScope.user.fullName ? sessionScope.user.fullName.substring(0,1).toUpperCase() : 'U'}
                </span>
                <span style="font-size:0.85rem; color:#e2e8f0; font-weight:600;">${sessionScope.user.fullName}</span>
                <a href="logout" style="color:#94a3b8; padding:4px 8px;" title="Logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="page-wrap small-wrap">
        <div class="panel">
            <h1 class="panel-title">Edit Book Listing</h1>
            <p class="panel-subtitle">Update textbook metadata, change exchange status, or revise valuation.</p>

            <c:if test="${not empty error}">
                <div class="alert error" style="margin-top:16px;">
                    <span>✕</span>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="edit-book" method="post" style="margin-top:20px;">
                <input type="hidden" name="id" value="${book.id}">

                <div class="two-col">
                    <div>
                        <label>Book Title *</label>
                        <input type="text" name="title" value="${book.title}" required>
                    </div>
                    <div>
                        <label>Author *</label>
                        <input type="text" name="author" value="${book.author}" required>
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>ISBN Number *</label>
                        <input type="text" name="isbn" value="${book.isbn}" required>
                    </div>
                    <div>
                        <label>Publisher</label>
                        <input type="text" name="publisher" value="${book.publisher}">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Category *</label>
                        <select name="category" required>
                            <option value="Academic" ${book.category == 'Academic' ? 'selected' : ''}>Academic</option>
                            <option value="Engineering" ${book.category == 'Engineering' ? 'selected' : ''}>Engineering</option>
                            <option value="Novel" ${book.category == 'Novel' ? 'selected' : ''}>Novel</option>
                            <option value="Competitive Exam" ${book.category == 'Competitive Exam' ? 'selected' : ''}>Competitive Exam</option>
                            <option value="Medical" ${book.category == 'Medical' ? 'selected' : ''}>Medical</option>
                            <option value="School" ${book.category == 'School' ? 'selected' : ''}>School</option>
                            <option value="Comics" ${book.category == 'Comics' ? 'selected' : ''}>Comics</option>
                            <option value="Others" ${book.category == 'Others' ? 'selected' : ''}>Others</option>
                        </select>
                    </div>
                    <div>
                        <label>Edition</label>
                        <input type="text" name="edition" value="${book.edition}">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Physical Condition</label>
                        <select name="condition">
                            <option value="Like New" ${book.condition == 'Like New' ? 'selected' : ''}>Like New</option>
                            <option value="Good" ${book.condition == 'Good' ? 'selected' : ''}>Good</option>
                            <option value="Fair" ${book.condition == 'Fair' ? 'selected' : ''}>Fair</option>
                            <option value="Poor" ${book.condition == 'Poor' ? 'selected' : ''}>Poor</option>
                        </select>
                    </div>
                    <div>
                        <label>Exchange Status</label>
                        <select name="status">
                            <option value="AVAILABLE" ${book.status == 'AVAILABLE' ? 'selected' : ''}>AVAILABLE (Open for Exchange)</option>
                            <option value="EXCHANGED" ${book.status == 'EXCHANGED' ? 'selected' : ''}>EXCHANGED (Completed)</option>
                        </select>
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Valuation Price (₹)</label>
                        <input type="number" name="sellingPrice" step="0.01" value="${book.price}">
                    </div>
                    <div>
                        <label>Language</label>
                        <input type="text" name="language" value="${book.language}">
                    </div>
                </div>

                <div>
                    <label>Description</label>
                    <textarea name="description" rows="3">${book.description}</textarea>
                </div>

                <div class="two-col">
                    <div>
                        <label>Preferred Subject</label>
                        <input type="text" name="preferredSubject" value="${book.preferredSubject}">
                    </div>
                    <div>
                        <label>Campus Location</label>
                        <input type="text" name="location" value="${book.location}">
                    </div>
                </div>

                <div style="display:flex; justify-content:space-between; align-items:center; margin-top:20px; padding-top:16px; border-top:1px solid var(--border);">
                    <a href="my-books" class="btn btn-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary" style="padding:12px 28px;">✓ Save Changes</button>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>