package com.bookexchange.servlet;

import com.bookexchange.dao.BookDao;
import com.bookexchange.model.Book;
import com.bookexchange.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String idStr = request.getParameter("id");
        String redirect = request.getParameter("redirect");
        if (redirect == null || redirect.trim().isEmpty()) {
            redirect = "my-books";
        }

        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int bookId = Integer.parseInt(idStr.trim());
                Book book = bookDao.getBookById(bookId);
                if (book != null && (book.getUserId() == user.getId() || "ADMIN".equalsIgnoreCase(user.getRole()))) {
                    bookDao.deleteBook(bookId);
                }
            } catch (NumberFormatException ignored) {
            }
        }
        response.sendRedirect(redirect);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

