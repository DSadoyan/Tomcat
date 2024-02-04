package com.digi.repository;

import com.digi.model.User;

public interface UserRepository {
    public User saveUser(User user);

    public User getByEmail(String email);

    public boolean verification(String email, String verifyCode);

    public void forgotPassword(String email);

    public void setPassword(String email, String newPassword, String confirmPassword);

    public User updateUser(String email, String name, String surname, String year);

    public void changePassword(String email, String oldPassword, String newPassword, String confirmPassword);

    public void deleteUser(int user_id);
}
