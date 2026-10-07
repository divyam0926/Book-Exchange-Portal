<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Login | Book Exchange Portal</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>

    <body class="auth-body">
        <div class="auth-card">
            <h2>Login</h2>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                    <form action="login" method="post" onsubmit="return validateLoginForm()">
                        <label>Email</label>
                        <input type="email" name="email" required>

                        <label>Password</label>
                        <input type="password" name="password" required>

                        <button type="submit" class="btn primary full">Login</button>
                    </form>
                    <p class="auth-link">Need an account? <a href="register">Register</a></p>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    </body>

    </html>