package com.example.JMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
@ToString
@Table(name = "mail")
public class Mail {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "mail", unique = true)
    private String mail;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EventType.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Column(name="condition", unique = true)
    private List<EventType> condition;
}