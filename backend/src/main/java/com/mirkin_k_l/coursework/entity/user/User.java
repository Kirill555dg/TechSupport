package com.mirkin_k_l.coursework.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mirkin_k_l.coursework.entity.application.Application;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "email")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

//        @Column(name = "active")
//        private Boolean active;


    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role; // Поле для единственной роли

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> assignedApplications = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> submittedApplications = new ArrayList<>();

    @JsonIgnore
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (lastName != null) fullName.append(lastName);
        if (firstName != null) fullName.append(" ").append(firstName);
        if (middleName != null) fullName.append(" ").append(middleName);
        return fullName.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", role=" + role +
                ", assignedApplications=" + assignedApplications +
                ", clientApplications=" + submittedApplications +
                '}';
    }
}
