package com.temx.security.address;

import com.temx.security.customers.Customer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressId;

    private String city;
    private String state;
    private  String country;
    private String addressType;

}
