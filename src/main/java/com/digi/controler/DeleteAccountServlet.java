package com.digi.controler;

import com.digi.model.User;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;
import com.digi.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String password = request.getParameter("password");
        User userDb = jpa.getByEmail(email);
        String errorMessage = null;
        if (!userDb.getPassword().equals(PasswordEncoder.encode(password))) {
            errorMessage = "Wrong password";
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/delete-account-page.jsp").forward(request, response);
        } else {
            session.removeAttribute("user");
            jpa.deleteUser(userDb.getId());
            response.sendRedirect("/login-page.jsp");
        }
    }
}
