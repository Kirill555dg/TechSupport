package com.mirkin_k_l.coursework.entity.application;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirkin_k_l.coursework.entity.employee.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING) // Хранить значения как строки в БД
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private User client;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private User employee;

}
