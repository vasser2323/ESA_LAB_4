package com.example.JMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String customerName;

    private String email;

    private String phoneNumber;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditCard> creditCards = new HashSet<>();
}
