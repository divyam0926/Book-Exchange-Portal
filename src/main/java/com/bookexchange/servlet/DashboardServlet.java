package com.bookexchange.servlet;

import com.bookexchange.dao.BookDao;
import com.bookexchange.dao.ExchangeRequestDao;
import com.bookexchange.model.Book;
import com.bookexchange.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();
    private final ExchangeRequestDao exchangeRequestDao = new ExchangeRequestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String search = request.getParameter("search");
        String category = request.getParameter("category");

        List<Book> books = bookDao.searchBooks(search, category);
        List<Integer> pendingBookIds = exchangeRequestDao.getPendingBookIdsForUser(currentUser.getId());
        int pendingIncomingCount = exchangeRequestDao.getPendingIncomingCount(currentUser.getId());

        // Update badge count in session for header navigation
        session.setAttribute("pendingIncomingCount", pendingIncomingCount);

        request.setAttribute("books", books);
        request.setAttribute("searchKeyword", search != null ? search : "");
        request.setAttribute("selectedCategory", category != null ? category : "All");
        request.setAttribute("pendingBookIds", pendingBookIds);

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
