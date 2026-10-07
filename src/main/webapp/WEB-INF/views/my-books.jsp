<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Books | Book Exchange Portal</title>
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

    <div class="page-wrap">
        <c:if test="${not empty param.success}">
            <div class="alert success">
                <span style="font-size:1.2rem;">✓</span>
                <span>${param.success}</span>
            </div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert error">
                <span style="font-size:1.2rem;">✕</span>
                <span>${param.error}</span>
            </div>
        </c:if>

        <div class="panel">
            <div class="panel-header">
                <div>
                    <h1 class="panel-title">My Book Listings (${myBooks.size()})</h1>
                    <p class="panel-subtitle">Manage books you listed on the platform, view exchange status, or update details.</p>
                </div>
                <a href="add-book" class="btn btn-primary">+ List Another Book</a>
            </div>
        </div>

        <c:if test="${empty myBooks}">
            <div class="panel empty-state">
                <div class="empty-state-icon">📚</div>
                <h3>You haven't listed any books yet</h3>
                <p>Have academic books or study notes from previous semesters? Share them with other students to start exchanging!</p>
                <a href="add-book" class="btn btn-primary" style="margin-top:12px;">+ List Your First Book</a>
            </div>
        </c:if>

        <div class="book-grid">
            <c:forEach var="book" items="${myBooks}">
                <div class="book-card">
                    <div class="book-card-top">
                        <div class="book-tags-row">
                            <span class="category-tag">${book.category}</span>
                            <span class="status-badge ${book.status == 'AVAILABLE' ? 'available' : 'exchanged'}">
                                ${book.status}
                            </span>
                        </div>
                        <h2 class="book-title">${book.title}</h2>
                        <p class="book-author">by <strong>${book.author}</strong></p>
                    </div>

                    <div class="book-card-body">
                        <div class="book-specs-row">
                            <span class="spec-chip">✨ ${book.condition}</span>
                            <c:if test="${not empty book.edition}">
                                <span class="spec-chip">Ed: ${book.edition}</span>
                            </c:if>
                            <c:if test="${book.price > 0}">
                                <span class="spec-chip">₹${book.price}</span>
                            </c:if>
                            <c:if test="${book.price == 0}">
                                <span class="spec-chip" style="background:#dcfce7; color:#15803d; font-weight:700;">Free / Swap</span>
                            </c:if>
                        </div>

                        <c:if test="${not empty book.description}">
                            <p class="book-desc-preview">${book.description}</p>
                        </c:if>

                        <div style="font-size:0.8rem; color:var(--text-subtle); margin-top:auto;">
                            Listed on: ${book.createdAt}
                        </div>
                    </div>

                    <div class="book-card-actions" style="gap:8px;">
                        <a href="book-details?id=${book.id}" class="btn btn-secondary btn-sm" style="flex:1;">View</a>
                        <a href="edit-book?id=${book.id}" class="btn btn-primary btn-sm" style="flex:1;">✏️ Edit</a>
                        <form action="delete-book" method="post" style="flex:1;" onsubmit="return confirm('Delete listing for ${book.title}?');">
                            <input type="hidden" name="id" value="${book.id}">
                            <input type="hidden" name="redirect" value="my-books">
                            <button type="submit" class="btn btn-danger btn-sm btn-full">🗑 Delete</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>