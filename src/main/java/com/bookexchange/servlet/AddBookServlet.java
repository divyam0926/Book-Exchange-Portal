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

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {
    private final BookDao bookDao = new BookDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");
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

        if (title == null || author == null || isbn == null || title.trim().isEmpty() || author.trim().isEmpty() || isbn.trim().isEmpty()) {
            request.setAttribute("error", "Book title, author, and ISBN are required.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
            return;
        }

        double price = 0.0;
        double originalPrice = 0.0;
        try {
            price = Double.parseDouble(priceStr != null && !priceStr.trim().isEmpty() ? priceStr.trim() : "0");
            originalPrice = Double.parseDouble(originalPriceStr != null && !originalPriceStr.trim().isEmpty() ? originalPriceStr.trim() : "0");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
            return;
        }

        Book book = new Book(user.getId(), title.trim(), author.trim(), isbn.trim(), category, condition, edition, description, price);
        book.setPublisher(publisher);
        book.setLanguage(language);
        book.setConditionDetails(conditionDetails);
        book.setMissingPages("yes".equalsIgnoreCase(request.getParameter("missingPages")));
        book.setDamagedCover("yes".equalsIgnoreCase(request.getParameter("damagedCover")));
        book.setOriginalPrice(originalPrice);
        book.setNegotiable("yes".equalsIgnoreCase(request.getParameter("negotiable")));
        book.setSaleMode(saleMode);
        book.setStatus(status == null || status.trim().isEmpty() ? "AVAILABLE" : status.toUpperCase());
        book.setPreferredCategory(preferredCategory);
        book.setPreferredAuthor(preferredAuthor);
        book.setPreferredSubject(preferredSubject);
        book.setCollege(college);
        book.setBranch(branch);
        book.setYearSemester(yearSemester);
        book.setLocation(location);
        book.setDeliveryOptions(deliveryOptions);
        book.setAdditionalNotes(additionalNotes);
        book.setSellingReason(sellingReason);
        book.setUsageDuration(usageDuration);

        if (publicationYearStr != null && !publicationYearStr.trim().isEmpty()) {
            try {
                book.setPublicationYear(Integer.parseInt(publicationYearStr.trim()));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Publication year must be a valid number.");
                request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
                return;
            }
        }

        boolean success = bookDao.addBook(book);

        if (success) {
            response.sendRedirect("dashboard");
        } else {
            request.setAttribute("error", "Book could not be added. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
        }
    }
}
