<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <title>Register | Book Exchange Portal</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>

    <body class="auth-body">
        <div class="auth-card wide">
            <h2>Create Account</h2>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert error">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>
                    <form action="register" method="post" onsubmit="return validateRegisterForm()">
                        <div class="two-col">
                            <div>
                                <label>Full Name</label>
                                <input type="text" name="fullName" required>
                            </div>
                            <div>
                                <label>Email</label>
                                <input type="email" name="email" required>
                            </div>
                        </div>

                        <div class="two-col">
                            <div>
                                <label>Password</label>
                                <input type="password" name="password" id="password" required>
                            </div>
                            <div>
                                <label>Phone</label>
                                <input type="text" name="phone">
                            </div>
                        </div>

                        <div class="two-col">
                            <div>
                                <label>Department</label>
                                <input type="text" name="department">
                            </div>
                            <div>
                                <label>Course</label>
                                <input type="text" name="course">
                            </div>
                        </div>

                        <button type="submit" class="btn primary full">Register</button>
                    </form>
                    <p class="auth-link">Already registered? <a href="login">Login</a></p>
        </div>

        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    </body>

    </html>