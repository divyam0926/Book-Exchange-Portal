package com.bookexchange.servlet;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.ExchangeRequestDao;
import com.bookexchange.model.Book;
import com.bookexchange.model.ExchangeRequest;
import com.bookexchange.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(urlPatterns = {"/exchange-requests", "/exchange-action"})
public class ExchangeRequestServlet extends HttpServlet {
    private final ExchangeRequestDao exchangeDao = new ExchangeRequestDao();
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String servletPath = request.getServletPath();

        if ("/exchange-action".equals(servletPath)) {
            // Forward action processing
            processAction(request, response, user);
            return;
        }

        List<ExchangeRequest> incoming = exchangeDao.getIncomingRequests(user.getId());
        List<ExchangeRequest> outgoing = exchangeDao.getOutgoingRequests(user.getId());

        int pendingIncomingCount = exchangeDao.getPendingIncomingCount(user.getId());
        session.setAttribute("pendingIncomingCount", pendingIncomingCount);

        request.setAttribute("incomingRequests", incoming);
        request.setAttribute("outgoingRequests", outgoing);
        request.setAttribute("activeTab", request.getParameter("tab") != null ? request.getParameter("tab") : "incoming");

        request.getRequestDispatcher("/WEB-INF/views/exchange-requests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        processAction(request, response, user);
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        String action = request.getParameter("action");
        String redirectUrl = request.getParameter("redirect");
        if (redirectUrl == null || redirectUrl.trim().isEmpty()) {
            redirectUrl = "exchange-requests";
        }

        if ("create".equalsIgnoreCase(action)) {
            String bookIdStr = request.getParameter("bookId");
            String notes = request.getParameter("notes");

            if (bookIdStr == null || bookIdStr.trim().isEmpty()) {
                redirectWithMessage(response, redirectUrl, "error", "Invalid book selection.");
                return;
            }

            int bookId;
            try {
                bookId = Integer.parseInt(bookIdStr.trim());
            } catch (NumberFormatException e) {
                redirectWithMessage(response, redirectUrl, "error", "Invalid book ID format.");
                return;
            }

            Book book = bookDao.getBookById(bookId);
            if (book == null) {
                redirectWithMessage(response, redirectUrl, "error", "The requested book was not found.");
                return;
            }

            if (book.getUserId() == user.getId()) {
                redirectWithMessage(response, redirectUrl, "error", "You cannot request an exchange for your own book.");
                return;
            }

            if (!"AVAILABLE".equalsIgnoreCase(book.getStatus())) {
                redirectWithMessage(response, redirectUrl, "error", "This book is not currently available for exchange.");
                return;
            }

            if (exchangeDao.hasPendingRequest(bookId, user.getId())) {
                redirectWithMessage(response, redirectUrl, "info", "You already have a pending exchange request for this book.");
                return;
            }

            ExchangeRequest req = new ExchangeRequest(bookId, user.getId(), book.getUserId(), notes);
            boolean success = exchangeDao.createRequest(req);

            if (success) {
                redirectWithMessage(response, redirectUrl, "success", "Exchange request sent successfully to " + (book.getOwnerName() != null ? book.getOwnerName() : "the owner") + "!");
            } else {
                redirectWithMessage(response, redirectUrl, "error", "Could not submit exchange request. Please try again.");
            }

        } else if ("accept".equalsIgnoreCase(action)) {
            int requestId = parseId(request.getParameter("requestId"));
            if (requestId <= 0) {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "error", "Invalid request ID.");
                return;
            }

            boolean ok = exchangeDao.acceptRequest(requestId, user.getId());
            if (ok) {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "success", "Exchange request accepted! The book is now marked as EXCHANGED.");
            } else {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "error", "Unable to accept request. Please verify ownership.");
            }

        } else if ("reject".equalsIgnoreCase(action)) {
            int requestId = parseId(request.getParameter("requestId"));
            if (requestId <= 0) {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "error", "Invalid request ID.");
                return;
            }

            boolean ok = exchangeDao.rejectRequest(requestId, user.getId());
            if (ok) {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "info", "Exchange request was declined.");
            } else {
                redirectWithMessage(response, "exchange-requests?tab=incoming", "error", "Unable to decline request.");
            }

        } else if ("cancel".equalsIgnoreCase(action)) {
            int requestId = parseId(request.getParameter("requestId"));
            if (requestId <= 0) {
                redirectWithMessage(response, "exchange-requests?tab=outgoing", "error", "Invalid request ID.");
                return;
            }

            boolean ok = exchangeDao.cancelRequest(requestId, user.getId());
            if (ok) {
                redirectWithMessage(response, "exchange-requests?tab=outgoing", "info", "Your exchange request was cancelled.");
            } else {
                redirectWithMessage(response, "exchange-requests?tab=outgoing", "error", "Unable to cancel request.");
            }

        } else {
            response.sendRedirect("exchange-requests");
        }
    }

    private int parseId(String val) {
        if (val == null) return -1;
        try {
            return Integer.parseInt(val.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void redirectWithMessage(HttpServletResponse response, String target, String type, String msg) throws IOException {
        String separator = target.contains("?") ? "&" : "?";
        String encodedMsg = URLEncoder.encode(msg, StandardCharsets.UTF_8.toString());
        response.sendRedirect(target + separator + type + "=" + encodedMsg);
    }
}

