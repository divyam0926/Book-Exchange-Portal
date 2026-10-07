<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book.title} | Book Details</title>
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
            <a href="my-books">My Books</a>
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

        <div style="margin-bottom: 20px;">
            <a href="dashboard" class="btn btn-secondary btn-sm">&larr; Back to Dashboard</a>
        </div>

        <div class="detail-grid-layout">
            <!-- Left Column: Owner & Book Status Card -->
            <div class="sidebar-col">
                <div class="panel">
                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:14px; padding-bottom:12px; border-bottom:1px solid var(--border);">
                        <span class="category-tag">${book.category}</span>
                        <span class="status-badge ${book.status == 'AVAILABLE' ? 'available' : 'exchanged'}">
                            ${book.status}
                        </span>
                    </div>

                    <h3 style="font-size:1.1rem; color:var(--text-main); margin-bottom:10px;">👤 Book Owner</h3>
                    <p style="font-size:1.05rem; font-weight:700; color:var(--primary); margin-bottom:4px;">
                        ${book.ownerName}
                    </p>
                    <c:if test="${not empty book.ownerDepartment}">
                        <p style="font-size:0.88rem; color:var(--text-muted);">Dept: ${book.ownerDepartment}</p>
                    </c:if>
                    <c:if test="${not empty book.ownerCourse}">
                        <p style="font-size:0.88rem; color:var(--text-muted);">Course: ${book.ownerCourse}</p>
                    </c:if>

                    <c:if test="${not empty book.location}">
                        <div style="margin-top:14px; padding-top:12px; border-top:1px dashed var(--border);">
                            <span style="font-size:0.8rem; font-weight:700; text-transform:uppercase; color:var(--text-subtle);">Campus / Area</span>
                            <p style="font-size:0.92rem; color:var(--text-main); font-weight:600;">${book.location}</p>
                        </div>
                    </c:if>

                    <c:if test="${not empty book.deliveryOptions}">
                        <div style="margin-top:10px;">
                            <span style="font-size:0.8rem; font-weight:700; text-transform:uppercase; color:var(--text-subtle);">Meetup / Pickup</span>
                            <p style="font-size:0.92rem; color:var(--text-main); font-weight:600;">${book.deliveryOptions}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Right Column: Specs & Exchange Form -->
            <div class="content-col">
                <div class="panel">
                    <h1 style="font-size:2.2rem; font-weight:800; color:var(--text-main); letter-spacing:-0.5px; margin-bottom:4px;">
                        ${book.title}
                    </h1>
                    <p style="font-size:1.15rem; color:var(--text-muted); margin-bottom:20px;">
                        by <strong>${book.author}</strong>
                    </p>

                    <!-- Technical Specifications Grid -->
                    <div class="detail-specs-table">
                        <div class="detail-spec-item">
                            <span>ISBN</span>
                            <span>${not empty book.isbn ? book.isbn : 'N/A'}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Category</span>
                            <span>${book.category}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Condition</span>
                            <span>${book.condition}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Edition</span>
                            <span>${not empty book.edition ? book.edition : 'Standard'}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Publisher</span>
                            <span>${not empty book.publisher ? book.publisher : 'N/A'}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Publication Year</span>
                            <span>${not empty book.publicationYear ? book.publicationYear : 'N/A'}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Language</span>
                            <span>${not empty book.language ? book.language : 'English'}</span>
                        </div>
                        <div class="detail-spec-item">
                            <span>Price / Valuation</span>
                            <span>${book.price > 0 ? '₹'.concat(book.price) : 'Free Exchange'}</span>
                        </div>
                    </div>

                    <c:if test="${not empty book.description}">
                        <div style="margin-bottom:22px;">
                            <h4 style="font-size:1.05rem; font-weight:700; color:var(--text-main); margin-bottom:6px;">Book Description</h4>
                            <p style="color:var(--text-muted); line-height:1.6;">${book.description}</p>
                        </div>
                    </c:if>

                    <c:if test="${not empty book.preferredSubject || not empty book.preferredCategory}">
                        <div style="background:#eff6ff; border:1px solid #bfdbfe; padding:16px 20px; border-radius:var(--radius-md); margin-bottom:22px;">
                            <h4 style="color:#1d4ed8; font-size:1rem; font-weight:700; margin-bottom:6px;">Exchange Subject Preference</h4>
                            <p style="color:#1e40af; font-size:0.92rem;">
                                The student prefers to exchange this book for:
                                <strong>${book.preferredSubject}</strong> ${not empty book.preferredCategory ? '('.concat(book.preferredCategory).concat(')') : ''}
                            </p>
                        </div>
                    </c:if>

                    <!-- Interaction Actions / Exchange Form -->
                    <div style="margin-top:28px; padding-top:20px; border-top:1px solid var(--border);">
                        <c:choose>
                            <c:when test="${isOwner}">
                                <div style="display:flex; gap:12px; align-items:center;">
                                    <a href="edit-book?id=${book.id}" class="btn btn-primary">✏️ Edit This Book</a>
                                    <form action="delete-book" method="post" style="display:inline;" onsubmit="return confirm('Permanently remove this book listing?');">
                                        <input type="hidden" name="id" value="${book.id}">
                                        <input type="hidden" name="redirect" value="my-books">
                                        <button type="submit" class="btn btn-danger">🗑 Delete Listing</button>
                                    </form>
                                </div>
                            </c:when>

                            <c:when test="${book.status != 'AVAILABLE'}">
                                <div class="alert info">
                                    <span>⚠️ This book has already been exchanged and is no longer available.</span>
                                </div>
                            </c:when>

                            <c:when test="${hasPendingRequest}">
                                <div class="alert info" style="background:#fef3c7; border-color:#fde68a; color:#b45309;">
                                    <span>⏳ You already submitted an exchange request for this book. Awaiting owner's response.</span>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <!-- NATIVE DIRECT EXCHANGE PROPOSAL FORM -->
                                <div style="background:#fafafa; border:2px solid var(--primary-border); padding:22px 24px; border-radius:var(--radius-md);">
                                    <h3 style="font-size:1.2rem; font-weight:800; color:var(--primary); margin-bottom:6px;">
                                        🤝 Request This Book for Exchange
                                    </h3>
                                    <p style="font-size:0.92rem; color:var(--text-muted); margin-bottom:16px;">
                                        Send an exchange request directly to <strong>${book.ownerName}</strong>. You can propose which book you want to exchange or mention a loan period.
                                    </p>

                                    <form action="exchange-action" method="post">
                                        <input type="hidden" name="action" value="create">
                                        <input type="hidden" name="redirect" value="book-details?id=${book.id}">
                                        <input type="hidden" name="bookId" value="${book.id}">

                                        <label for="proposalNotes">Proposal Message (Optional):</label>
                                        <textarea id="proposalNotes" name="notes" rows="3" placeholder="e.g. Hi ${book.ownerName}, I would like to exchange this for my MCA semester textbook."></textarea>

                                        <button type="submit" class="btn btn-primary" style="padding:12px 24px; font-size:1rem; margin-top:8px;">
                                            ✓ Send Exchange Request Now
                                        </button>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>