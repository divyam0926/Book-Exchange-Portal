<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Dashboard | Book Exchange Portal</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        </head>

        <body>
            <nav class="topbar dashboard-nav">
                <div class="logo">Book Exchange Portal</div>
                <div class="nav-links">
                    <a href="dashboard">Home</a>
                    <a href="add-book">Add Book</a>
                    <a href="logout">Logout</a>
                </div>
            </nav>

            <div class="page-wrap">
                <div class="panel">
                    <h2>Available Books</h2>
                    <form class="search-form" method="get" action="dashboard">
                        <input type="text" name="search" value="${searchKeyword}" placeholder="Search by title, author, category or ISBN">
                        <button type="submit" class="btn primary">Search</button>
                    </form>
                </div>

                <div class="book-grid">
                    <c:forEach var="book" items="${books}">
                        <div class="book-card">
                            <span class="status">${book.status}</span>
                            <h3>${book.title}</h3>
                            <p><strong>Author:</strong> ${book.author}</p>
                            <p><strong>Category:</strong> ${book.category}</p>
                            <p><strong>ISBN:</strong> ${book.isbn}</p>
                            <p><strong>Condition:</strong> ${book.condition}</p>
                            <p><strong>Edition:</strong> ${book.edition}</p>
                            <p><strong>Price:</strong> $${book.price}</p>
                            <p class="description">${book.description}</p>
                            <button class="btn secondary full" onclick="alert('Exchange request feature can be implemented in the next phase.')">Request Exchange</button>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </body>

        </html>