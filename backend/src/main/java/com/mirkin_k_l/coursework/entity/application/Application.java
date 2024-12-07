package com.mirkin_k_l.coursework.entity.application;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mirkin_k_l.coursework.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "applications")
@JsonInclude(JsonInclude.Include.NON_NULL) // Исключить null-значения из JSON
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "creation_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dateOfCreation;

    @Column(name = "status", nullable = false)
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

    @PrePersist
    private void init() {
        dateOfCreation = LocalDateTime.now();
    }

    // Возвращаем ID клиента в JSON
//    @JsonProperty("clientId")
//    public Long getClientId() {
//        return client != null ? client.getId() : null;
//    }

    @JsonProperty("employeeName")
    public String getEmployeeName() {
        return employee != null ? employee.getFullName() : null;
    }

    @JsonProperty("employeeEmail")
    public String getEmployeeEmail() {
        return employee != null ? employee.getEmail() : null;
    }


    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", status=" + status +
                // ", client=" + getClientId() +
                ", employee=" + getEmployeeName() + getEmployeeEmail() +
                '}';
    }
}
