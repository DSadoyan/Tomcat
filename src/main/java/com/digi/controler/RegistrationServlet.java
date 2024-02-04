package com.digi.controler;

import com.digi.enums.Status;
import com.digi.exceptions.ResourceAlreadyExistException;
import com.digi.exceptions.ValidationException;
import com.digi.model.User;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;
import com.digi.util.GenerateToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String year = request.getParameter("year");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int newYear = 0;
        String errorMessage = null;
        try {
            newYear = Integer.parseInt(year);
            String verifyCode = GenerateToken.generateVerifyCode();
            User user = new User(0, name, surname, Integer.parseInt(year), email, password, verifyCode, null, Status.INACTIVE);
            jpa.saveUser(user);
        } catch (NumberFormatException e) {
            errorMessage = "Wrong year format";
        } catch (ValidationException | ResourceAlreadyExistException e) {
            errorMessage = e.getMessage();
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/registration-page.jsp").forward(request, response);
        } else {
            session.setAttribute("email", email);
            response.sendRedirect("/verification-page.jsp");
        }
    }
}
