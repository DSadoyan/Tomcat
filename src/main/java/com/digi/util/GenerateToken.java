package com.digi.util;

import org.apache.commons.lang3.RandomStringUtils;

public class GenerateToken {
    public static String generateVerifyCode() {
        return RandomStringUtils.random(6, true, true);
    }
    public static String generateResetToken(){
        return RandomStringUtils.random(8,false,true);
    }
}
