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
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        String search = request.getParameter("search");
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookDao.searchBooks(search.trim());
        } else {
            books = bookDao.getAllBooks();
        }

        request.setAttribute("books", books);
        request.setAttribute("searchKeyword", search);
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
