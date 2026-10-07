package com.bookexchange.servlet;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.ExchangeRequestDao;
import com.bookexchange.dao.UserDao;
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
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    private final BookDao bookDao = new BookDao();
    private final ExchangeRequestDao exchangeDao = new ExchangeRequestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect("dashboard");
            return;
        }

        List<User> users = userDao.getAllUsers();
        List<Book> books = bookDao.getAllBooks();
        List<ExchangeRequest> requests = exchangeDao.getAllRequests();

        long availableCount = books.stream().filter(b -> "AVAILABLE".equalsIgnoreCase(b.getStatus())).count();
        long exchangedCount = books.stream().filter(b -> "EXCHANGED".equalsIgnoreCase(b.getStatus())).count();

        request.setAttribute("users", users);
        request.setAttribute("books", books);
        request.setAttribute("requests", requests);
        request.setAttribute("availableCount", availableCount);
        request.setAttribute("exchangedCount", exchangedCount);

        request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect("dashboard");
            return;
        }

        String action = request.getParameter("action");
        if ("deleteUser".equalsIgnoreCase(action)) {
            String uidStr = request.getParameter("userId");
            if (uidStr != null) {
                try {
                    int uid = Integer.parseInt(uidStr.trim());
                    if (uid != currentUser.getId()) {
                        userDao.deleteUser(uid);
                    }
                } catch (NumberFormatException ignored) {}
            }
        } else if ("toggleRole".equalsIgnoreCase(action)) {
            String uidStr = request.getParameter("userId");
            String newRole = request.getParameter("role");
            if (uidStr != null && newRole != null) {
                try {
                    int uid = Integer.parseInt(uidStr.trim());
                    userDao.setRole(uid, newRole.toUpperCase());
                } catch (NumberFormatException ignored) {}
            }
        } else if ("deleteBook".equalsIgnoreCase(action)) {
            String bidStr = request.getParameter("bookId");
            if (bidStr != null) {
                try {
                    int bid = Integer.parseInt(bidStr.trim());
                    bookDao.deleteBook(bid);
                } catch (NumberFormatException ignored) {}
            }
        }

        response.sendRedirect("admin");
    }
}

