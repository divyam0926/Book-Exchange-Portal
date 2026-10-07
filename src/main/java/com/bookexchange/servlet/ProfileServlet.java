package com.bookexchange.servlet;

import com.bookexchange.dao.UserDao;
import com.bookexchange.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        User freshUser = userDao.getUserById(sessionUser.getId());
        if (freshUser != null) {
            session.setAttribute("user", freshUser);
            request.setAttribute("profileUser", freshUser);
        } else {
            request.setAttribute("profileUser", sessionUser);
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String formType = request.getParameter("formType");

        if ("password".equalsIgnoreCase(formType)) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (currentPassword == null || newPassword == null || confirmPassword == null ||
                currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                request.setAttribute("passwordError", "All password fields are required.");
                doGet(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("passwordError", "New passwords do not match.");
                doGet(request, response);
                return;
            }

            if (newPassword.length() < 6) {
                request.setAttribute("passwordError", "New password must be at least 6 characters.");
                doGet(request, response);
                return;
            }

            User dbUser = userDao.getUserById(currentUser.getId());
            if (dbUser == null || !BCrypt.checkpw(currentPassword, dbUser.getPassword())) {
                request.setAttribute("passwordError", "Current password is incorrect.");
                doGet(request, response);
                return;
            }

            boolean ok = userDao.updatePassword(currentUser.getId(), newPassword);
            if (ok) {
                request.setAttribute("passwordSuccess", "Password updated successfully!");
            } else {
                request.setAttribute("passwordError", "Failed to update password. Please try again.");
            }
            doGet(request, response);

        } else {
            // Profile details update
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String department = request.getParameter("department");
            String course = request.getParameter("course");

            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("profileError", "Full Name is required.");
                doGet(request, response);
                return;
            }

            User updated = new User();
            updated.setId(currentUser.getId());
            updated.setFullName(fullName.trim());
            updated.setPhone(phone != null ? phone.trim() : "");
            updated.setDepartment(department != null ? department.trim() : "");
            updated.setCourse(course != null ? course.trim() : "");

            boolean success = userDao.updateProfile(updated);
            if (success) {
                User freshUser = userDao.getUserById(currentUser.getId());
                session.setAttribute("user", freshUser);
                request.setAttribute("profileSuccess", "Profile updated successfully!");
            } else {
                request.setAttribute("profileError", "Failed to update profile. Please try again.");
            }
            doGet(request, response);
        }
    }
}

