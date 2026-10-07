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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet("/add-book")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 25 * 1024 * 1024)
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
        String title = value(request, "title");
        String author = value(request, "author");
        String isbn = value(request, "isbn");
        String category = value(request, "category");
        String condition = value(request, "condition");
        String edition = value(request, "edition");
        String publisher = value(request, "publisher");
        String language = value(request, "language");
        String conditionDetails = value(request, "conditionDetails");
        String saleMode = value(request, "saleMode");
        String status = value(request, "status");
        String preferredCategory = value(request, "preferredCategory");
        String preferredAuthor = value(request, "preferredAuthor");
        String preferredSubject = value(request, "preferredSubject");
        String college = value(request, "college");
        String branch = value(request, "branch");
        String yearSemester = value(request, "yearSemester");
        String location = value(request, "location");
        String deliveryOptions = value(request, "deliveryOptions");
        String additionalNotes = value(request, "additionalNotes");
        String sellingReason = value(request, "sellingReason");
        String usageDuration = value(request, "usageDuration");
        String description = value(request, "description");
        String priceStr = value(request, "sellingPrice");
        String originalPriceStr = value(request, "originalPrice");
        String publicationYearStr = value(request, "publicationYear");

        if (title == null || author == null || isbn == null || title.trim().isEmpty() || author.trim().isEmpty() || isbn.trim().isEmpty()) {
            request.setAttribute("error", "Book title, author, and ISBN are required.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
            return;
        }

        double price = 0.0;
        double originalPrice = 0.0;
        try {
            price = Double.parseDouble(priceStr != null ? priceStr : "0");
            originalPrice = Double.parseDouble(originalPriceStr != null ? originalPriceStr : "0");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
            return;
        }

        Book book = new Book(user.getId(), title.trim(), author.trim(), isbn.trim(), category, condition, edition, description, price);
        book.setPublisher(publisher);
        book.setLanguage(language);
        book.setConditionDetails(conditionDetails);
        book.setMissingPages("yes".equalsIgnoreCase(value(request, "missingPages")));
        book.setDamagedCover("yes".equalsIgnoreCase(value(request, "damagedCover")));
        book.setOriginalPrice(originalPrice);
        book.setNegotiable("yes".equalsIgnoreCase(value(request, "negotiable")));
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
                book.setPublicationYear(Integer.parseInt(publicationYearStr));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Publication year must be a valid number.");
                request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
                return;
            }
        }
        String[] imagePaths = saveImages(request);
        book.setImage1(imagePaths[0]);
        book.setImage2(imagePaths[1]);
        book.setImage3(imagePaths[2]);
        book.setImage4(imagePaths[3]);
        book.setImage5(imagePaths[4]);
        boolean success = bookDao.addBook(book);

        if (success) {
            response.sendRedirect("dashboard");
        } else {
            request.setAttribute("error", "Book could not be added. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/add-book.jsp").forward(request, response);
        }
    }

    private String[] saveImages(HttpServletRequest request) throws IOException, ServletException {
        String[] paths = new String[5];
        Path uploadDirectory = Paths.get(getServletContext().getRealPath("/uploads"));
        Files.createDirectories(uploadDirectory);
        int index = 0;
        for (Part part : request.getParts()) {
            if (!part.getName().startsWith("image") || part.getSize() == 0 || index == paths.length) {
                continue;
            }
            String contentType = part.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                continue;
            }
            String extension = contentType.substring("image/".length()).replaceAll("[^a-zA-Z0-9]", "");
            String fileName = UUID.randomUUID() + "." + extension;
            Path destination = uploadDirectory.resolve(fileName);
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, destination);
            }
            paths[index++] = "uploads/" + fileName;
        }
        return paths;
    }

    private String value(HttpServletRequest request, String name) throws IOException, ServletException {
        String value = request.getParameter(name);
        if (value != null) {
            return value;
        }
        Part part = request.getPart(name);
        if (part == null || part.getSize() == 0) {
            return null;
        }
        try (InputStream input = part.getInputStream()) {
            return new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
    }
}
