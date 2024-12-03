package com.mirkin_k_l.coursework.repository;


import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT t FROM Application t WHERE t.employee.id = :employeeId ORDER BY t.id ASC")
    List<Application> findByEmployee_Id(@Param("employeeId") Long employeeId);

    @Modifying
    @Query("UPDATE Application a SET a.status = :status WHERE a.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") ApplicationStatus status);
}
