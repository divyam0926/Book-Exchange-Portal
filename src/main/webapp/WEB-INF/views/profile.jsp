<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile | Book Exchange Portal</title>
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
            <a href="profile" class="active">Profile</a>
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
            <h1 class="panel-title">Student Profile & Settings</h1>
            <p class="panel-subtitle">Manage your institutional identity and account credentials.</p>
        </div>

        <c:if test="${not empty profileSuccess}">
            <div class="alert success">
                <span style="font-size:1.2rem;">✓</span>
                <span>${profileSuccess}</span>
            </div>
        </c:if>
        <c:if test="${not empty profileError}">
            <div class="alert error">
                <span style="font-size:1.2rem;">✕</span>
                <span>${profileError}</span>
            </div>
        </c:if>
        <c:if test="${not empty passwordSuccess}">
            <div class="alert success">
                <span style="font-size:1.2rem;">✓</span>
                <span>${passwordSuccess}</span>
            </div>
        </c:if>
        <c:if test="${not empty passwordError}">
            <div class="alert error">
                <span style="font-size:1.2rem;">✕</span>
                <span>${passwordError}</span>
            </div>
        </c:if>

        <div class="panel" style="margin-bottom: 24px;">
            <div style="display:flex; align-items:center; gap:16px; margin-bottom:20px; padding-bottom:16px; border-bottom:1px solid var(--border);">
                <div style="width:54px; height:54px; border-radius:50%; background:linear-gradient(135deg, var(--primary), #8b5cf6); color:white; display:flex; align-items:center; justify-content:center; font-size:1.5rem; font-weight:800;">
                    ${not empty profileUser.fullName ? profileUser.fullName.substring(0,1).toUpperCase() : 'U'}
                </div>
                <div>
                    <h2 style="font-size:1.35rem; font-weight:800; color:var(--text-main); margin-bottom:2px;">
                        ${profileUser.fullName}
                    </h2>
                    <p style="font-size:0.9rem; color:var(--text-muted);">
                        ${profileUser.department} &bull; ${profileUser.course} &bull; <span class="category-tag" style="padding:2px 6px;">${profileUser.role}</span>
                    </p>
                </div>
            </div>

            <h3 style="font-size:1.1rem; color:var(--primary); margin-bottom:12px;">Institutional Information</h3>
            <form action="profile" method="post">
                <input type="hidden" name="formType" value="details">

                <div class="two-col">
                    <div>
                        <label>Full Name *</label>
                        <input type="text" name="fullName" value="${profileUser.fullName}" required>
                    </div>
                    <div>
                        <label>Email Address</label>
                        <input type="email" value="${profileUser.email}" disabled style="background:#f1f5f9; color:#64748b;">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Contact Number (Mobile)</label>
                        <input type="text" name="phone" value="${profileUser.phone}" placeholder="10-digit phone number">
                    </div>
                    <div>
                        <label>Department</label>
                        <input type="text" name="department" value="${profileUser.department}" placeholder="e.g. Computer Science, MCA">
                    </div>
                </div>

                <div class="two-col">
                    <div>
                        <label>Course / Degree</label>
                        <input type="text" name="course" value="${profileUser.course}" placeholder="e.g. MCA Semester 2">
                    </div>
                    <div>
                        <label>Account Role</label>
                        <input type="text" value="${profileUser.role}" disabled style="background:#f1f5f9; color:#64748b;">
                    </div>
                </div>

                <div style="margin-top: 14px;">
                    <button type="submit" class="btn btn-primary">✓ Update Profile Details</button>
                </div>
            </form>
        </div>

        <div class="panel">
            <h3 style="font-size:1.1rem; color:var(--primary); margin-bottom:12px;">Security & Password</h3>
            <form action="profile" method="post">
                <input type="hidden" name="formType" value="password">

                <div>
                    <label>Current Password *</label>
                    <input type="password" name="currentPassword" required placeholder="Enter current password">
                </div>

                <div class="two-col">
                    <div>
                        <label>New Password *</label>
                        <input type="password" name="newPassword" required minlength="6" placeholder="At least 6 characters">
                    </div>
                    <div>
                        <label>Confirm New Password *</label>
                        <input type="password" name="confirmPassword" required minlength="6" placeholder="Repeat new password">
                    </div>
                </div>

                <div style="margin-top: 14px;">
                    <button type="submit" class="btn btn-secondary">Update Password</button>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>