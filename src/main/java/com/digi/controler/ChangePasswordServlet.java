package com.digi.controler;

import com.digi.exceptions.BadRequestException;
import com.digi.exceptions.ValidationException;
import com.digi.model.User;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String errorMessage = null;
        try {
            jpa.changePassword(email, oldPassword, newPassword, confirmPassword);
        }catch (BadRequestException | ValidationException e){
            errorMessage = e.getMessage();
        }
        if (errorMessage != null){
            request.setAttribute("errorMessage",errorMessage);
            request.getRequestDispatcher("/change-password-page.jsp").forward(request,response);
        }else {
            session.removeAttribute("user");
            response.sendRedirect("/login-page.jsp");
        }
    }
}
