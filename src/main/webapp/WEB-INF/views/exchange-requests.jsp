<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exchange Requests | Book Exchange Portal</title>
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
            <a href="exchange-requests" class="active">
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
        <c:if test="${not empty param.info}">
            <div class="alert info">
                <span style="font-size:1.2rem;">ℹ</span>
                <span>${param.info}</span>
            </div>
        </c:if>

        <div class="panel">
            <div class="panel-header" style="margin-bottom:8px;">
                <div>
                    <h1 class="panel-title">Exchange Request Inbox</h1>
                    <p class="panel-subtitle">Review swap requests from peers or monitor the status of books you requested.</p>
                </div>
            </div>

            <!-- Tab Navigation -->
            <div class="tabs-nav">
                <a href="exchange-requests?tab=incoming" class="tab-link ${activeTab == 'incoming' ? 'active' : ''}">
                    📥 Incoming Requests (${incomingRequests.size()})
                </a>
                <a href="exchange-requests?tab=outgoing" class="tab-link ${activeTab == 'outgoing' ? 'active' : ''}">
                    📤 My Sent Requests (${outgoingRequests.size()})
                </a>
            </div>
        </div>

        <c:choose>
            <c:when test="${activeTab == 'incoming'}">
                <!-- TAB 1: INCOMING REQUESTS -->
                <c:if test="${empty incomingRequests}">
                    <div class="panel empty-state">
                        <div class="empty-state-icon">📭</div>
                        <h3>No incoming requests yet</h3>
                        <p>When another student wants to exchange one of your listed books, their request will appear here for you to accept or decline.</p>
                        <a href="add-book" class="btn btn-primary">+ List More Books</a>
                    </div>
                </c:if>

                <c:forEach var="req" items="${incomingRequests}">
                    <div class="request-card ${req.status == 'ACCEPTED' ? 'accepted' : ''}">
                        <div class="request-top-row">
                            <div>
                                <h3 class="request-book-heading">${req.bookTitle}</h3>
                                <p style="font-size:0.9rem; color:var(--text-muted); margin-top:2px;">
                                    by ${req.bookAuthor} &bull; <span class="category-tag" style="padding:2px 8px; font-size:0.7rem;">${req.bookCategory}</span>
                                </p>
                            </div>
                            <span class="req-badge ${req.status.toLowerCase()}">${req.status}</span>
                        </div>

                        <div class="request-body-grid">
                            <div class="student-info-box">
                                <h5>Requested By</h5>
                                <p style="font-weight:700; color:var(--text-main); font-size:1.05rem;">${req.requesterName}</p>
                                <p style="font-size:0.88rem; color:var(--text-muted);">${req.requesterDepartment} &bull; ${req.requesterCourse}</p>
                                <p style="font-size:0.88rem; color:var(--text-main); margin-top:4px;">📧 ${req.requesterEmail}</p>
                                <c:if test="${not empty req.requesterPhone}">
                                    <p style="font-size:0.88rem; color:var(--text-main);">📱 ${req.requesterPhone}</p>
                                </c:if>
                            </div>

                            <div>
                                <span style="font-size:0.8rem; font-weight:700; text-transform:uppercase; color:var(--text-subtle);">Student's Proposal Note</span>
                                <div class="proposal-note-box" style="margin-top:6px;">
                                    <c:choose>
                                        <c:when test="${not empty req.notes}">
                                            "${req.notes}"
                                        </c:when>
                                        <c:otherwise>
                                            <em>(No custom note provided)</em>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <small style="color:var(--text-subtle); display:block; margin-top:8px;">Received on: ${req.requestedAt}</small>
                            </div>
                        </div>

                        <!-- Actions Bar -->
                        <div style="padding-top:14px; border-top:1px solid #f1f5f9; display:flex; gap:12px; align-items:center;">
                            <c:choose>
                                <c:when test="${req.status == 'PENDING'}">
                                    <form action="exchange-action" method="post" style="display:inline-block;" onsubmit="return confirm('Accept this exchange request? The book will be marked as EXCHANGED.');">
                                        <input type="hidden" name="action" value="accept">
                                        <input type="hidden" name="requestId" value="${req.id}">
                                        <button type="submit" class="btn btn-success">✓ Accept Exchange</button>
                                    </form>
                                    <form action="exchange-action" method="post" style="display:inline-block;" onsubmit="return confirm('Decline this request?');">
                                        <input type="hidden" name="action" value="reject">
                                        <input type="hidden" name="requestId" value="${req.id}">
                                        <button type="submit" class="btn btn-danger">✕ Decline</button>
                                    </form>
                                </c:when>

                                <c:when test="${req.status == 'ACCEPTED'}">
                                    <div class="accepted-contact-banner" style="width:100%;">
                                        <span style="font-size:1.5rem;">🎉</span>
                                        <div>
                                            <strong>Exchange Accepted!</strong> Contact ${req.requesterName} at <strong>${req.requesterEmail}</strong> or <strong>${req.requesterPhone}</strong> to exchange the book.
                                        </div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <span style="color:var(--text-subtle); font-style:italic; font-size:0.9rem;">This request was declined.</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- TAB 2: OUTGOING REQUESTS -->
                <c:if test="${empty outgoingRequests}">
                    <div class="panel empty-state">
                        <div class="empty-state-icon">📤</div>
                        <h3>You haven't requested any books yet</h3>
                        <p>Browse available textbooks listed by peers and click 'Request Exchange' to start your first exchange!</p>
                        <a href="dashboard" class="btn btn-primary">+ Browse Available Books</a>
                    </div>
                </c:if>

                <c:forEach var="req" items="${outgoingRequests}">
                    <div class="request-card ${req.status == 'ACCEPTED' ? 'accepted' : ''}">
                        <div class="request-top-row">
                            <div>
                                <h3 class="request-book-heading">${req.bookTitle}</h3>
                                <p style="font-size:0.9rem; color:var(--text-muted); margin-top:2px;">
                                    by ${req.bookAuthor} &bull; <span class="category-tag" style="padding:2px 8px; font-size:0.7rem;">${req.bookCategory}</span>
                                </p>
                            </div>
                            <span class="req-badge ${req.status.toLowerCase()}">${req.status}</span>
                        </div>

                        <div class="request-body-grid">
                            <div class="student-info-box">
                                <h5>Book Owner</h5>
                                <p style="font-weight:700; color:var(--text-main); font-size:1.05rem;">${req.ownerName}</p>
                                <p style="font-size:0.88rem; color:var(--text-muted);">${req.ownerDepartment} &bull; ${req.ownerCourse}</p>
                                <c:if test="${req.status == 'ACCEPTED'}">
                                    <p style="font-size:0.88rem; color:var(--text-main); margin-top:4px;">📧 ${req.ownerEmail}</p>
                                    <p style="font-size:0.88rem; color:var(--text-main);">📱 ${req.ownerPhone}</p>
                                </c:if>
                            </div>

                            <div>
                                <span style="font-size:0.8rem; font-weight:700; text-transform:uppercase; color:var(--text-subtle);">Your Note</span>
                                <div class="proposal-note-box" style="margin-top:6px;">
                                    <c:choose>
                                        <c:when test="${not empty req.notes}">
                                            "${req.notes}"
                                        </c:when>
                                        <c:otherwise>
                                            <em>(No note attached)</em>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <small style="color:var(--text-subtle); display:block; margin-top:8px;">Sent on: ${req.requestedAt}</small>
                            </div>
                        </div>

                        <div style="padding-top:14px; border-top:1px solid #f1f5f9; display:flex; gap:12px; align-items:center;">
                            <c:choose>
                                <c:when test="${req.status == 'PENDING'}">
                                    <form action="exchange-action" method="post" onsubmit="return confirm('Cancel this exchange request?');">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="requestId" value="${req.id}">
                                        <button type="submit" class="btn btn-secondary btn-sm">Cancel Request</button>
                                    </form>
                                </c:when>

                                <c:when test="${req.status == 'ACCEPTED'}">
                                    <div class="accepted-contact-banner" style="width:100%;">
                                        <span style="font-size:1.5rem;">🎉</span>
                                        <div>
                                            <strong>Exchange Accepted!</strong> Reach out to <strong>${req.ownerName}</strong> at <strong>${req.ownerEmail}</strong> or <strong>${req.ownerPhone}</strong> to complete the book swap.
                                        </div>
                                    </div>
                                </c:when>

                                <c:when test="${req.status == 'CANCELLED'}">
                                    <span style="color:var(--text-subtle); font-style:italic; font-size:0.9rem;">You cancelled this request.</span>
                                </c:when>

                                <c:otherwise>
                                    <span style="color:var(--text-subtle); font-style:italic; font-size:0.9rem;">Owner declined this exchange request.</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/script.js?v=20261007b"></script>
</body>
</html>
