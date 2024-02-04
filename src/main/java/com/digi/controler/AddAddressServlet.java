package com.digi.controler;

import com.digi.model.Address;
import com.digi.model.User;
import com.digi.repository.AddressRepositoryImpl;

import com.digi.repository.AddressRepositoryJPAImpl;
import com.digi.repository.UserRepositoryImpl;
import com.digi.repository.UserRepositoryJPAImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddAddressServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddressRepositoryJPAImpl aJpa = new AddressRepositoryJPAImpl();
        UserRepositoryJPAImpl jpa = new UserRepositoryJPAImpl();
        AddressRepositoryImpl addressRepository = new AddressRepositoryImpl();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String home = request.getParameter("home");

        User userDb = jpa.getByEmail(email);
        Address address = new Address(0, country, city, street, home, userDb.getId());
        aJpa.saveAddress(address);
        request.getRequestDispatcher("/home-page.jsp").forward(request, response);

    }
}
