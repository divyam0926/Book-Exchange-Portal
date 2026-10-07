package com.bookexchange.servlet;

import com.bookexchange.dao.BookDao;
import com.bookexchange.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/api/books")
public class BookApiServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("search");
        String category = request.getParameter("category");

        List<Book> books = bookDao.searchBooks(keyword, category);

        PrintWriter out = response.getWriter();
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            json.append("{")
                .append("\"id\":").append(b.getId()).append(",")
                .append("\"userId\":").append(b.getUserId()).append(",")
                .append("\"title\":\"").append(escapeJson(b.getTitle())).append("\",")
                .append("\"author\":\"").append(escapeJson(b.getAuthor())).append("\",")
                .append("\"isbn\":\"").append(escapeJson(b.getIsbn())).append("\",")
                .append("\"category\":\"").append(escapeJson(b.getCategory())).append("\",")
                .append("\"condition\":\"").append(escapeJson(b.getCondition())).append("\",")
                .append("\"price\":").append(b.getPrice()).append(",")
                .append("\"status\":\"").append(escapeJson(b.getStatus())).append("\",")
                .append("\"ownerName\":\"").append(escapeJson(b.getOwnerName())).append("\"")
                .append("}");
            if (i < books.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        out.print(json.toString());
        out.flush();
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

