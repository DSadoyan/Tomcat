package com.digi.controler;

import com.digi.exceptions.BadRequestException;
import com.digi.exceptions.NotFoundException;
import com.digi.exceptions.ValidationException;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String errorMessage = null;
        try {
            jpa.forgotPassword(email);
        } catch (NotFoundException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/forgot-password-page.jsp").forward(request, response);
        } else {
            session.setAttribute("email", email);
            response.getWriter().println("It's sent successfully");

        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        String email = (String) request.getSession().getAttribute("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String errorMessage = null;
        try {
            jpa.setPassword(email, newPassword, confirmPassword);
        } catch (ValidationException | BadRequestException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/set-password-page.jsp").forward(request, response);
        } else {
            session.removeAttribute("email");
            response.sendRedirect("/login-page.jsp");
        }

    }

}
