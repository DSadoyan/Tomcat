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

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = jpa.getByEmail(email);
        String errorMessage = null;
        if (user == null){
            errorMessage = "Wrong password or email";
        } else {
            if (!user.getPassword().equals(PasswordEncoder.encode(password))){
                errorMessage = "Wrong password or email";
            }
        }
        if (errorMessage != null){
            request.setAttribute("errorMessage",errorMessage);
            request.getRequestDispatcher("/login-page.jsp").forward(request,response);
        } else {
            session.setAttribute("user",user);
            request.getRequestDispatcher("/home-page.jsp").forward(request,response);
        }


    }
}
