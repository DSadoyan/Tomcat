package com.digi.model;

import com.digi.enums.Status;
import lombok.*;
import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    private int id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surname;
    private int year;
    private String email;
    private String password;
    @Column(name = "verification_code")
    private String verifyCode;
    @Column(name = "reset_token")
    private String resetToken;
    @Enumerated(EnumType.STRING)
    private Status status;

    public User(String name, String surname, int year, String email) {
        this.name = name;
        this.surname = surname;
        this.year = year;
        this.email = email;
    }
}
