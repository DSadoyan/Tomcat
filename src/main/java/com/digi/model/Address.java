package com.digi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
@ToString

public class Address {
    @Id
    @Column(name = "address_id")
    private int addressId;
    private String country;
    private String city;
    private String street;
    private String home;
    @Column(name = "user_id")
    private int userId;
}
