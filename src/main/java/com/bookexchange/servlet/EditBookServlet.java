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

@WebServlet("/edit-book")
public class EditBookServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect("my-books");
            return;
        }

        try {
            int bookId = Integer.parseInt(idStr.trim());
            Book book = bookDao.getBookById(bookId);
            if (book == null || (book.getUserId() != user.getId() && !"ADMIN".equalsIgnoreCase(user.getRole()))) {
                response.sendRedirect("my-books");
                return;
            }

            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/views/edit-book.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("my-books");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("my-books");
            return;
        }

        int bookId = Integer.parseInt(idStr.trim());
        Book existing = bookDao.getBookById(bookId);
        if (existing == null || (existing.getUserId() != user.getId() && !"ADMIN".equalsIgnoreCase(user.getRole()))) {
            response.sendRedirect("my-books");
            return;
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String category = request.getParameter("category");
        String condition = request.getParameter("condition");
        String edition = request.getParameter("edition");
        String publisher = request.getParameter("publisher");
        String language = request.getParameter("language");
        String conditionDetails = request.getParameter("conditionDetails");
        String saleMode = request.getParameter("saleMode");
        String status = request.getParameter("status");
        String preferredCategory = request.getParameter("preferredCategory");
        String preferredAuthor = request.getParameter("preferredAuthor");
        String preferredSubject = request.getParameter("preferredSubject");
        String college = request.getParameter("college");
        String branch = request.getParameter("branch");
        String yearSemester = request.getParameter("yearSemester");
        String location = request.getParameter("location");
        String deliveryOptions = request.getParameter("deliveryOptions");
        String additionalNotes = request.getParameter("additionalNotes");
        String sellingReason = request.getParameter("sellingReason");
        String usageDuration = request.getParameter("usageDuration");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("sellingPrice");
        String originalPriceStr = request.getParameter("originalPrice");
        String publicationYearStr = request.getParameter("publicationYear");

        double price = 0.0;
        double originalPrice = 0.0;
        try {
            price = Double.parseDouble(priceStr != null && !priceStr.trim().isEmpty() ? priceStr.trim() : "0");
            originalPrice = Double.parseDouble(originalPriceStr != null && !originalPriceStr.trim().isEmpty() ? originalPriceStr.trim() : "0");
        } catch (NumberFormatException ignored) {
        }

        existing.setTitle(title != null ? title.trim() : existing.getTitle());
        existing.setAuthor(author != null ? author.trim() : existing.getAuthor());
        existing.setIsbn(isbn != null ? isbn.trim() : existing.getIsbn());
        existing.setCategory(category);
        existing.setCondition(condition);
        existing.setEdition(edition);
        existing.setPublisher(publisher);
        existing.setLanguage(language);
        existing.setConditionDetails(conditionDetails);
        existing.setDescription(description);
        existing.setPrice(price);
        existing.setOriginalPrice(originalPrice);
        existing.setNegotiable("yes".equalsIgnoreCase(request.getParameter("negotiable")));
        existing.setMissingPages("yes".equalsIgnoreCase(request.getParameter("missingPages")));
        existing.setDamagedCover("yes".equalsIgnoreCase(request.getParameter("damagedCover")));
        existing.setSaleMode(saleMode);
        existing.setStatus(status != null && !status.trim().isEmpty() ? status.toUpperCase() : existing.getStatus());
        existing.setPreferredCategory(preferredCategory);
        existing.setPreferredAuthor(preferredAuthor);
        existing.setPreferredSubject(preferredSubject);
        existing.setCollege(college);
        existing.setBranch(branch);
        existing.setYearSemester(yearSemester);
        existing.setLocation(location);
        existing.setDeliveryOptions(deliveryOptions);
        existing.setAdditionalNotes(additionalNotes);
        existing.setSellingReason(sellingReason);
        existing.setUsageDuration(usageDuration);

        if (publicationYearStr != null && !publicationYearStr.trim().isEmpty()) {
            try {
                existing.setPublicationYear(Integer.parseInt(publicationYearStr.trim()));
            } catch (NumberFormatException ignored) {
            }
        }

        boolean ok = bookDao.updateBook(existing);
        if (ok) {
            response.sendRedirect("book-details?id=" + bookId);
        } else {
            request.setAttribute("error", "Failed to update book.");
            request.setAttribute("book", existing);
            request.getRequestDispatcher("/WEB-INF/views/edit-book.jsp").forward(request, response);
        }
    }
}
