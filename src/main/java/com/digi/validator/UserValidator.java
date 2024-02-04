package com.digi.validator;

import com.digi.exceptions.ValidationException;
import com.digi.model.User;

public class UserValidator {
    private static final String NAME_REGEX = "[A-Z][a-z]+";
    private static final String NAME_NULL_MSG = "User name can not be null or empty";
    private static final String SURNAME_REGEX = "[A-Z][a-z]+";
    private static final String SURNAME_NULL_MSG = "User surname can not be null or empty";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String EMAIL_MSG = "Email is not correct";
    private static final String YEAR_MSG = "Wrong year";

    public static void validateFields(User user) {
        String name = user.getName();
        String surname = user.getSurname();
        int year = user.getYear();
        String email = user.getEmail();


        if (!name.matches(NAME_REGEX)) {
            throw new ValidationException(NAME_NULL_MSG);
        }
        if (!surname.matches(SURNAME_REGEX)) {
            throw new ValidationException(SURNAME_NULL_MSG);
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new ValidationException(EMAIL_MSG);
        }
        if (year < 1910 || year > 2023) {
            throw new ValidationException(YEAR_MSG);
        }
    }

    public static void validatePassword(String password) {
        if (password == null) {
            throw new ValidationException("Password can not be null");
        }
        if (password.length() < 8) {
            throw new ValidationException("Short password");
        }
        int countOfDigits = 0;
        int countOfUpperCase = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c))
                countOfDigits++;
            else if (Character.isUpperCase(c))
                countOfUpperCase++;
        }
        if (countOfDigits < 1 || countOfUpperCase < 1)
            throw new ValidationException("Password is not valid");
    }


}
