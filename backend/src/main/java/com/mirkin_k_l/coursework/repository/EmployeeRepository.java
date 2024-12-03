package com.mirkin_k_l.coursework.repository;

import com.mirkin_k_l.coursework.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
