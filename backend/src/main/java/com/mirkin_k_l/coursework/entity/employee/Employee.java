package com.mirkin_k_l.coursework.entity.employee;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
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
@Table(name = "employees")
@EqualsAndHashCode(of = "email")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

    @Transient
    private Integer countApplications;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();

    public Long getCountApplications(){
        Long count = 0L;

        for (Application application : applications) {
            if (application.getStatus() != ApplicationStatus.DONE) {
                count++;
            }
        }

        return count;
    }
}
