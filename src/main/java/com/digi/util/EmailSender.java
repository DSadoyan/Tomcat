package com.digi.util;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

public class EmailSender {
    private static final String PASSWORD = "vsnbobxaeykkotvq";
    private static final String USERNAME = "bookingsystembook@yandex.ru";

    public static void sendEmail(String to, String subject, String text) {
        String host = "smtp.yandex.ru";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
        try {
            EmailUtil.sendEmail(session, to, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
