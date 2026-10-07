<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Book | Book Exchange Portal</title>
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
            <a href="add-book" class="active">+ Add Book</a>
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

    <div class="page-wrap small-wrap">
        <div class="panel">
            <h1 class="panel-title">Add a Book for Exchange</h1>
            <p class="panel-subtitle">Share your textbook details with other students looking to study or swap.</p>

            <c:if test="${not empty error}">
                <div class="alert error" style="margin-top:16px;">
                    <span>✕</span>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="add-book" method="post" style="margin-top:20px;">
                <h3 style="font-size:1.1rem; color:var(--primary); border-bottom:2px solid #eef2ff; padding-bottom:6px; margin-bottom:12px;">
                    1. Basic Book Information
                </h3>

                <div class="two-col">
                    <div>
                        <label>Book Title *</label>
                        <input type="text" name="title" placeholder="e.g. Operating System Concepts" required>
                    </div>
                    <div>
                        <label>Author *</label>
                        <input type="text" name="author" placeholder="e.g. Silberschatz, Galvin" required>
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>ISBN Number *</label>
                        <input type="text" name="isbn" placeholder="e.g. 978-0470128725" required>
                    </div>
                    <div>
                        <label>Publisher</label>
                        <input type="text" name="publisher" placeholder="e.g. Wiley, Pearson">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Publication Year</label>
                        <input type="number" name="publicationYear" min="1950" max="2030" placeholder="e.g. 2021">
                    </div>
                    <div>
                        <label>Language</label>
                        <input type="text" name="language" value="English">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Category / Domain *</label>
                        <select name="category" required>
                            <option value="Academic">Academic</option>
                            <option value="Engineering">Engineering</option>
                            <option value="Novel">Novel</option>
                            <option value="Competitive Exam">Competitive Exam</option>
                            <option value="Medical">Medical</option>
                            <option value="School">School</option>
                            <option value="Comics">Comics</option>
                            <option value="Others">Others</option>
                        </select>
                    </div>
                    <div>
                        <label>Edition</label>
                        <input type="text" name="edition" placeholder="e.g. 10th Edition">
                    </div>
                </div>

                <h3 style="font-size:1.1rem; color:var(--primary); border-bottom:2px solid #eef2ff; padding-bottom:6px; margin:16px 0 12px;">
                    2. Condition & Valuation
                </h3>

                <div class="two-col">
                    <div>
                        <label>Physical Condition *</label>
                        <select name="condition">
                            <option value="Like New">Like New (Mint condition)</option>
                            <option value="Good" selected>Good (Minor wear)</option>
                            <option value="Fair">Fair (Readable, highlights present)</option>
                            <option value="Poor">Poor (Heavily used)</option>
                        </select>
                    </div>
                    <div>
                        <label>Selling / Valuation Price (₹)</label>
                        <input type="number" name="sellingPrice" step="0.01" min="0" placeholder="0 for Free Exchange">
                    </div>
                </div>

                <div>
                    <label>Brief Description</label>
                    <textarea name="description" rows="3" placeholder="Provide any details about the edition, usefulness for particular subjects, etc."></textarea>
                </div>

                <h3 style="font-size:1.1rem; color:var(--primary); border-bottom:2px solid #eef2ff; padding-bottom:6px; margin:16px 0 12px;">
                    3. Exchange Preferences & Campus Info
                </h3>

                <div class="two-col">
                    <div>
                        <label>Preferred Subject to Receive</label>
                        <input type="text" name="preferredSubject" placeholder="e.g. DBMS, Data Structures">
                    </div>
                    <div>
                        <label>Preferred Delivery / Meetup</label>
                        <select name="deliveryOptions">
                            <option value="Campus Library Pickup">Campus Library Pickup</option>
                            <option value="Department Notice Area">Department Notice Area</option>
                            <option value="Campus Canteen / Central Meetup">Campus Canteen / Central Meetup</option>
                            <option value="Hand-to-hand Classroom Exchange">Hand-to-hand Classroom Exchange</option>
                        </select>
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Campus Location / Hall</label>
                        <input type="text" name="location" placeholder="e.g. Academic Block 2, Computer Lab">
                    </div>
                    <div>
                        <label>Listing Status</label>
                        <select name="status">
                            <option value="AVAILABLE" selected>AVAILABLE (Open for Requests)</option>
                        </select>
                    </div>
                </div>

                <div style="display:flex; justify-content:space-between; align-items:center; margin-top:20px; padding-top:16px; border-top:1px solid var(--border);">
                    <a href="dashboard" class="btn btn-secondary">Cancel</a>
                    <button type="submit" class="btn btn-primary" style="padding:12px 28px;">✓ Publish Book Listing</button>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>