<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Admin Dashboard | Book Exchange Portal</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        </head>

    <nav class="topbar">
        <div class="logo">
            <a href="dashboard">
                <span>📚</span>
                <span>Book Exchange Portal</span>
            </a>
            <span class="logo-badge" style="background:#dc2626;">ADMIN</span>
        </div>
        <div class="nav-links">
            <a href="dashboard">Home</a>
            <a href="add-book">+ Add Book</a>
            <a href="my-books">My Books</a>
            <a href="exchange-requests">Exchange Requests</a>
            <a href="profile">Profile</a>
            <a href="admin" class="admin-nav-link active">Admin Panel</a>
            <div class="nav-user">
                <span class="user-avatar" style="background:#dc2626;">A</span>
                <span style="font-size:0.85rem; color:#e2e8f0; font-weight:600;">Administrator</span>
                <a href="logout" style="color:#94a3b8; padding:4px 8px;" title="Logout">Logout</a>
            </div>
        </div>
    </nav>

            <div class="page-wrap">
                <div class="panel">
                    <h2>Administration Dashboard</h2>
                    <p style="color: #6b7280;">System metrics, user management, and portal exchange activity logs.</p>

                    <div class="stat-grid" style="display:grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap:16px; margin-top:20px;">
                        <div class="stat-card">
                            <h4>Total Registered Users</h4>
                            <span class="stat-number">${users.size()}</span>
                        </div>
                        <div class="stat-card">
                            <h4>Total Books Listed</h4>
                            <span class="stat-number">${books.size()}</span>
                        </div>
                        <div class="stat-card">
                            <h4>Available for Exchange</h4>
                            <span class="stat-number" style="color:#16a34a;">${availableCount}</span>
                        </div>
                        <div class="stat-card">
                            <h4>Completed Exchanges</h4>
                            <span class="stat-number" style="color:#2563eb;">${exchangedCount}</span>
                        </div>
                        <div class="stat-card">
                            <h4>Total Exchange Requests</h4>
                            <span class="stat-number" style="color:#d97706;">${requests.size()}</span>
                        </div>
                    </div>
                </div>

                <!-- Section 1: User Management -->
                <div class="panel">
                    <h3>Module 1: User Management</h3>
                    <div style="overflow-x: auto; margin-top:14px;">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Department</th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td>${u.id}</td>
                                        <td><strong>${u.fullName}</strong></td>
                                        <td>${u.email}</td>
                                        <td>${u.phone}</td>
                                        <td>${u.department}</td>
                                        <td>
                                            <span class="tag ${u.role == 'ADMIN' ? 'tag-admin' : 'tag-user'}">${u.role}</span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${u.id == sessionScope.user.id}">
                                                    <small style="color:#6b7280;">(Logged in)</small>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="admin" method="post" style="display:inline-block;">
                                                        <input type="hidden" name="action" value="toggleRole">
                                                        <input type="hidden" name="userId" value="${u.id}">
                                                        <input type="hidden" name="role" value="${u.role == 'ADMIN' ? 'USER' : 'ADMIN'}">
                                                        <button type="submit" class="btn-sm secondary">
                                                    Make ${u.role == 'ADMIN' ? 'USER' : 'ADMIN'}
                                                </button>
                                                    </form>
                                                    <form action="admin" method="post" style="display:inline-block;" onsubmit="return confirm('Delete user ${u.fullName}? All their books and exchange requests will be removed.');">
                                                        <input type="hidden" name="action" value="deleteUser">
                                                        <input type="hidden" name="userId" value="${u.id}">
                                                        <button type="submit" class="btn-sm danger">Delete</button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Section 2: Book Management -->
                <div class="panel">
                    <h3>Module 2: Book Management</h3>
                    <div style="overflow-x: auto; margin-top:14px;">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Author</th>
                                    <th>Category</th>
                                    <th>Status</th>
                                    <th>Listed By</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="b" items="${books}">
                                    <tr>
                                        <td>${b.id}</td>
                                        <td><strong>${b.title}</strong></td>
                                        <td>${b.author}</td>
                                        <td>${b.category}</td>
                                        <td>
                                            <span class="status ${b.status == 'AVAILABLE' ? 'status-avail' : 'status-exchanged'}">${b.status}</span>
                                        </td>
                                        <td>${b.ownerName} (${b.ownerEmail})</td>
                                        <td>
                                            <a href="book-details?id=${b.id}" class="btn-sm secondary">View</a>
                                            <form action="admin" method="post" style="display:inline-block;" onsubmit="return confirm('Delete book: ${b.title}?');">
                                                <input type="hidden" name="action" value="deleteBook">
                                                <input type="hidden" name="bookId" value="${b.id}">
                                                <button type="submit" class="btn-sm danger">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Section 3: Exchange Activities -->
                <div class="panel">
                    <h3>Module 3 & 4: Monitor Exchange Activities</h3>
                    <div style="overflow-x: auto; margin-top:14px;">
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Book Title</th>
                                    <th>Requester Student</th>
                                    <th>Book Owner</th>
                                    <th>Status</th>
                                    <th>Date Requested</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="r" items="${requests}">
                                    <tr>
                                        <td>${r.id}</td>
                                        <td><strong>${r.bookTitle}</strong></td>
                                        <td>${r.requesterName} (${r.requesterEmail})</td>
                                        <td>${r.ownerName} (${r.ownerEmail})</td>
                                        <td>
                                            <span class="req-status status-${r.status.toLowerCase()}">${r.status}</span>
                                        </td>
                                        <td>${r.requestedAt}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </body>

        </html>