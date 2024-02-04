package com.digi.controler;

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

public class EditInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String year = request.getParameter("year");
        String errorMessage = null;
        User userDb = null;
        try {
            userDb = jpa.updateUser(email, name, surname, year);
        }catch (ValidationException e){
            errorMessage = e.getMessage();
        }
        if (errorMessage != null){
            request.setAttribute("errorMessage",errorMessage);
            request.getRequestDispatcher("/edit-user-info-page.jsp").forward(request,response);
        } else {
            session.setAttribute("user",userDb);
            request.getRequestDispatcher("/home-page.jsp").forward(request,response);
        }

    }
}
