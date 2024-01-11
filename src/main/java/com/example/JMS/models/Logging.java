package com.example.JMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "logging")
public class Logging {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "entity", unique = true)
    private String entity;

    @Column(name = "entity_id")
    private int entity_id;

}