package com.TTLTTBDD.server.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "oders")
public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oder", nullable = false)
    private Integer id;

    @Column(name = "dateOrder", nullable = false)
    private LocalDate dateOrder;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

}