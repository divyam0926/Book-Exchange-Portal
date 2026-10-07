<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | Book Exchange Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=20261007b">
</head>
<body class="auth-body">
    <div class="auth-card wide">
        <div style="text-align:center; margin-bottom: 24px;">
            <div style="font-size:2.5rem; margin-bottom:8px;">🎓</div>
            <h2 style="font-size:1.8rem; font-weight:800; color:var(--text-main);">Create Student Account</h2>
            <p class="subtitle">Join fellow students to swap, lend, and borrow textbooks</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert error">
                <span>✕</span>
                <span><%= request.getAttribute("error") %></span>
            </div>
        <% } %>

        <form action="register" method="post" onsubmit="return validateRegisterForm()">
            <div class="two-col">
                <div>
                    <label>Full Name *</label>
                    <input type="text" name="fullName" placeholder="e.g. Divya Sundar" required>
                </div>
                <div>
                    <label>Email Address *</label>
                    <input type="email" name="email" placeholder="student@college.edu" required>
                </div>
            </div>

            <div class="two-col">
                <div>
                    <label>Password * (Minimum 6 characters)</label>
                    <input type="password" name="password" id="password" placeholder="Create a secure password" required minlength="6">
                </div>
                <div>
                    <label>Mobile Number</label>
                    <input type="text" name="phone" placeholder="10-digit phone number">
                </div>
            </div>

            <div class="two-col">
                <div>
                    <label>Department</label>
                    <input type="text" name="department" placeholder="e.g. Computer Applications">
                </div>
                <div>
                    <label>Degree / Class</label>
                    <input type="text" name="course" placeholder="e.g. MCA 1st Year">
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-full" style="padding:12px; margin-top:12px;">
                Complete Registration &rarr;
            </button>
        </form>

        <p class="auth-link" style="margin-top:20px;">
            Already have an account? <a href="login">Sign In here</a>
        </p>
        <p style="text-align:center; margin-top:12px;">
            <a href="index.jsp" style="color:var(--text-muted); font-size:0.85rem; text-decoration:none;">&larr; Back to Home</a>
        </p>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>