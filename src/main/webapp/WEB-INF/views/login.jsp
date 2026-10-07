<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Book Exchange Portal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=20261007b">
</head>
<body class="auth-body">
    <div class="auth-card">
        <div style="text-align:center; margin-bottom: 24px;">
            <div style="font-size:2.5rem; margin-bottom:8px;">📚</div>
            <h2 style="font-size:1.8rem; font-weight:800; color:var(--text-main);">Welcome Back</h2>
            <p class="subtitle">Log in to your Book Exchange Portal account</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert error">
                <span>✕</span>
                <span><%= request.getAttribute("error") %></span>
            </div>
        <% } %>

        <form action="login" method="post" onsubmit="return validateLoginForm()">
            <div>
                <label>Email Address</label>
                <input type="email" name="email" placeholder="name@college.edu" required autofocus>
            </div>

            <div>
                <label>Password</label>
                <input type="password" name="password" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn btn-primary btn-full" style="padding:12px; margin-top:8px;">
                Sign In &rarr;
            </button>
        </form>

        <p class="auth-link" style="margin-top:20px;">
            Don't have an account? <a href="register">Create student account</a>
        </p>
        <p style="text-align:center; margin-top:12px;">
            <a href="index.jsp" style="color:var(--text-muted); font-size:0.85rem; text-decoration:none;">&larr; Back to Home</a>
        </p>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>