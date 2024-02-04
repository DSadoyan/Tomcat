package com.digi.util;

import java.util.Base64;

public class PasswordEncoder {

    public static String encode(String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(password.getBytes());
    }

}
