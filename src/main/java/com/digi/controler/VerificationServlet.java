package com.digi.controler;

import com.digi.exceptions.BadRequestException;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerificationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        String verifyCode = request.getParameter("verifyCode");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String errorMessage = null;
        try {
            jpa.verification(email, verifyCode);
        } catch (BadRequestException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage == null) {
            session.removeAttribute("email");
            response.sendRedirect("/login-page.jsp");
        } else {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/verification-page.jsp").forward(request, response);
        }
    }
}
