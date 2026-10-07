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

@WebServlet("/book-details")
public class BookDetailsServlet extends HttpServlet {
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
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("dashboard");
            return;
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            Book book = bookDao.getBookById(id);
            if (book == null) {
                response.sendRedirect("dashboard");
                return;
            }

            boolean isOwner = (book.getUserId() == currentUser.getId());
            boolean hasPending = exchangeDao.hasPendingRequest(book.getId(), currentUser.getId());

            request.setAttribute("book", book);
            request.setAttribute("isOwner", isOwner);
            request.setAttribute("hasPendingRequest", hasPending);

            request.getRequestDispatcher("/WEB-INF/views/book-details.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("dashboard");
        }
    }
}

