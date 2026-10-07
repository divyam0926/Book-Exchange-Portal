<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | Book Exchange Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=20261007b">
</head>
<body>
    <!-- Topbar Navigation -->
    <nav class="topbar">
        <div class="logo">
            <a href="dashboard">
                <span>📚</span>
                <span>Book Exchange Portal</span>
            </a>
            <span class="logo-badge">MCA</span>
        </div>
        <div class="nav-links">
            <a href="dashboard" class="active">Home</a>
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
                <span style="font-size:0.85rem; color:#e2e8f0; font-weight:600;">
                    ${sessionScope.user.fullName}
                </span>
                <a href="logout" style="color:#94a3b8; padding:4px 8px; margin-left:4px;" title="Logout">Logout</a>
            </div>
        </div>
    </nav>

    <!-- Main Container -->
    <div class="page-wrap">
        <!-- Toast Alerts -->
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
        <c:if test="${not empty param.info}">
            <div class="alert info">
                <span style="font-size:1.2rem;">ℹ</span>
                <span>${param.info}</span>
            </div>
        </c:if>

        <!-- Search & Filter Banner -->
        <div class="panel">
            <div class="panel-header">
                <div>
                    <h1 class="panel-title">Available Books for Exchange</h1>
                    <p class="panel-subtitle">Find textbooks and reference materials shared by fellow students.</p>
                </div>
                <a href="add-book" class="btn btn-primary">+ List a Book</a>
            </div>

            <form method="get" action="dashboard" style="margin-top: 8px;">
                <div class="search-container">
                    <div class="search-input-wrap">
                        <input type="text" name="search" value="${searchKeyword}" placeholder="Search by title, author, subject, or ISBN...">
                    </div>
                    <div class="category-select-wrap">
                        <select name="category" onchange="this.form.submit()">
                            <option value="All" ${selectedCategory == 'All' ? 'selected' : ''}>All Categories</option>
                            <option value="Academic" ${selectedCategory == 'Academic' ? 'selected' : ''}>Academic</option>
                            <option value="Novel" ${selectedCategory == 'Novel' ? 'selected' : ''}>Novel</option>
                            <option value="Competitive Exam" ${selectedCategory == 'Competitive Exam' ? 'selected' : ''}>Competitive Exam</option>
                            <option value="Engineering" ${selectedCategory == 'Engineering' ? 'selected' : ''}>Engineering</option>
                            <option value="Medical" ${selectedCategory == 'Medical' ? 'selected' : ''}>Medical</option>
                            <option value="School" ${selectedCategory == 'School' ? 'selected' : ''}>School</option>
                            <option value="Comics" ${selectedCategory == 'Comics' ? 'selected' : ''}>Comics</option>
                            <option value="Others" ${selectedCategory == 'Others' ? 'selected' : ''}>Others</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Search</button>
                    <c:if test="${not empty searchKeyword || selectedCategory != 'All'}">
                        <a href="dashboard" class="btn btn-secondary">Clear Filters</a>
                    </c:if>
                </div>
            </form>

            <!-- Quick Category Pill Filter -->
            <div class="filter-pills">
                <a href="dashboard" class="pill-link ${selectedCategory == 'All' ? 'active' : ''}">All</a>
                <a href="dashboard?category=Academic" class="pill-link ${selectedCategory == 'Academic' ? 'active' : ''}">Academic</a>
                <a href="dashboard?category=Engineering" class="pill-link ${selectedCategory == 'Engineering' ? 'active' : ''}">Engineering</a>
                <a href="dashboard?category=Novel" class="pill-link ${selectedCategory == 'Novel' ? 'active' : ''}">Novel</a>
                <a href="dashboard?category=Competitive+Exam" class="pill-link ${selectedCategory == 'Competitive Exam' ? 'active' : ''}">Competitive Exam</a>
                <a href="dashboard?category=Medical" class="pill-link ${selectedCategory == 'Medical' ? 'active' : ''}">Medical</a>
                <a href="dashboard?category=School" class="pill-link ${selectedCategory == 'School' ? 'active' : ''}">School</a>
            </div>
        </div>

        <!-- Empty Results State -->
        <c:if test="${empty books}">
            <div class="panel empty-state">
                <div class="empty-state-icon">📖</div>
                <h3>No books found matching your search</h3>
                <p>Try searching with another keyword or category, or share a new book for exchange.</p>
                <a href="add-book" class="btn btn-primary" style="margin-top:12px;">+ List a Book Now</a>
            </div>
        </c:if>

        <!-- Books Grid -->
        <div class="book-grid">
            <c:forEach var="book" items="${books}">
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

                        <div class="book-owner-chip">
                            <span>👤 Listed by: <strong>${book.ownerName}</strong></span>
                            <c:if test="${not empty book.ownerDepartment}">
                                <span>&bull; ${book.ownerDepartment}</span>
                            </c:if>
                        </div>
                    </div>

                    <div class="book-card-actions">
                        <a href="book-details?id=${book.id}" class="btn btn-secondary" style="flex:1;">View Details</a>

                        <c:choose>
                            <c:when test="${book.userId == sessionScope.user.id}">
                                <span class="badge-muted">Your Listing</span>
                            </c:when>
                            <c:when test="${book.status != 'AVAILABLE'}">
                                <span class="badge-muted">Exchanged</span>
                            </c:when>
                            <c:when test="${pendingBookIds.contains(book.id)}">
                                <span class="badge-pending">Request Pending</span>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-primary" style="flex:1;" onclick="toggleExchangeDrawer(${book.id})">
                                    🤝 Request Exchange
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Collapsible Inline Request Form Drawer -->
                    <c:if test="${book.userId != sessionScope.user.id && book.status == 'AVAILABLE' && !pendingBookIds.contains(book.id)}">
                        <div id="drawer-${book.id}" class="exchange-drawer">
                            <div class="exchange-drawer-title">
                                <span>Send Request to ${book.ownerName}</span>
                                <button type="button" onclick="toggleExchangeDrawer(${book.id})" style="background:none; border:none; font-size:1.2rem; cursor:pointer; color:#64748b;">&times;</button>
                            </div>
                            <form action="exchange-action" method="post">
                                <input type="hidden" name="action" value="create">
                                <input type="hidden" name="redirect" value="dashboard">
                                <input type="hidden" name="bookId" value="${book.id}">
                                <textarea name="notes" rows="2" placeholder="Write a note (e.g. 'Hi, I would like to exchange this for my MCA semester book')"></textarea>
                                <button type="submit" class="btn btn-primary btn-full btn-sm">
                                    ✓ Send Exchange Request
                                </button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Inline script for instant, fail-proof drawer toggle -->
    <script>
        function toggleExchangeDrawer(id) {
            var drawer = document.getElementById('drawer-' + id);
            if (!drawer) return;
            if (drawer.classList.contains('open')) {
                drawer.classList.remove('open');
            } else {
                document.querySelectorAll('.exchange-drawer').forEach(function(d) {
                    d.classList.remove('open');
                });
                drawer.classList.add('open');
                var ta = drawer.querySelector('textarea');
                if (ta) ta.focus();
            }
        }
    </script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>