package com.mirkin_k_l.coursework.repository;


import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByEmployeeId(Long employeeId, Sort sort);

    List<Application> findByClientId(Long id, Sort sort);


    List<Application> findByDateOfCreationBetween(LocalDateTime from, LocalDateTime to, Sort sort);

    List<Application> findByDateOfCreationAfter(LocalDateTime from, Sort sort);

    List<Application> findByDateOfCreationBefore(LocalDateTime to, Sort sort);
}
