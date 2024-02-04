package com.digi.repository;

import com.digi.enums.Status;
import com.digi.exceptions.BadRequestException;
import com.digi.exceptions.NotFoundException;
import com.digi.exceptions.ResourceAlreadyExistException;
import com.digi.model.Address;
import com.digi.model.User;
import com.digi.util.EmailSender;
import com.digi.util.GenerateToken;
import com.digi.util.MyDataSource;
import com.digi.util.PasswordEncoder;
import com.digi.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private static final String REDIRECT_URL = "http://localhost:8080/set-password-page.jsp";

    public User saveUser(User user) {
        UserValidator.validateFields(user);
        UserValidator.validatePassword(user.getPassword());
        String verifyCode = user.getVerifyCode();
        User userDB = getByEmail(user.getEmail());
        if (userDB != null) {
            throw new ResourceAlreadyExistException("Resource already exist with given email");
        }

        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into users values (0,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getYear());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, PasswordEncoder.encode(user.getPassword()));
            preparedStatement.setString(6, verifyCode);
            preparedStatement.setString(7, user.getResetToken());
            preparedStatement.setString(8, user.getStatus().toString());
            preparedStatement.executeUpdate();
            EmailSender.sendEmail(user.getEmail(), "verification code",
                    "Your verification code is " + verifyCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User getByEmail(String email) {
        Connection connection = MyDataSource.getConnection();
        User user = null;
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from users where email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int year = resultSet.getInt("year");
                String password = resultSet.getString("password");
                String verificationCode = resultSet.getString("verification_code");
                String resetToken = resultSet.getString("reset_token");
                String status = resultSet.getString("status");
                user = new User(id, firstName, lastName, year, email, password,
                        verificationCode, resetToken, Enum.valueOf(Status.class, status));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean verification(String email, String verifyCode) {
        User user = getByEmail(email);
        if (!user.getVerifyCode().equals(verifyCode)) {
            throw new BadRequestException("Wrong verify code");
        }
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set verification_code =?,status = ? where email = ?");
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, Status.ACTIVE.toString());
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void forgotPassword(String email) {
        User user = getByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found with given email");
        }
        String token = GenerateToken.generateResetToken();
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set reset_token = ? where email = ?");
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        EmailSender.sendEmail(user.getEmail(), "Redirect url", "Please click here " + REDIRECT_URL);
    }

    public void setPassword(String email, String newPassword, String confirmPassword) {

        if (newPassword != null) {
            UserValidator.validatePassword(newPassword);
            if (!newPassword.equals(confirmPassword)) {
                throw new BadRequestException("Passwords don't match");
            }
        }
        User user = getByEmail(email);
        if (user == null) {
            throw new BadRequestException("Your password is already changed");
        } else if (user.getResetToken() == null) {
            throw new BadRequestException("Your password is already changed");
        }
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set password = ?,reset_token = ? where email = ?");
            preparedStatement.setString(1, PasswordEncoder.encode(newPassword));
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User updateUser(String email, String name, String surname, String year) {
        User userDb = getByEmail(email);
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) && !StringUtils.isEmpty(year)) {
            UserValidator.validateFields(new User(name, surname, Integer.parseInt(year), email));
        }
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set first_name = ?,last_name = ?,year = ? where email = ?");
            preparedStatement.setString(1, StringUtils.isEmpty(name) ? userDb.getName() : name);
            preparedStatement.setString(2, StringUtils.isEmpty(surname) ? userDb.getSurname() : surname);
            preparedStatement.setInt(3, StringUtils.isEmpty(year) ? userDb.getYear() : Integer.parseInt(year));
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDb;
    }

    public void changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
        if (!StringUtils.isEmpty(newPassword)) {
            UserValidator.validatePassword(newPassword);
            if (!newPassword.equals(confirmPassword)) {
                throw new BadRequestException("Passwords don't match");
            }
        }
        User user = getByEmail(email);
        if (!user.getPassword().equals(PasswordEncoder.encode(oldPassword))) {
            throw new BadRequestException("Wrong old password");
        }
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update users set password = ? where email = ?");
            preparedStatement.setString(1, PasswordEncoder.encode(newPassword));
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int user_id) {
        AddressRepositoryJPAImpl aJpa = new AddressRepositoryJPAImpl();
        AddressRepositoryImpl addressRepository = new AddressRepositoryImpl();
        Address address = aJpa.getAddressByUserId(user_id);
        if (address != null) {
            aJpa.deleteAddress(address.getAddressId());
        }
        Connection connection = MyDataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from users where id = ?");
            preparedStatement.setInt(1, user_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

