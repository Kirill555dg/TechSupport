package com.mirkin_k_l.coursework.service;


import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import com.mirkin_k_l.coursework.entity.user.User;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.repository.ApplicationRepository;
import com.mirkin_k_l.coursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    public List<Application> findByEmployee(Long id) {
        return applicationRepository.findByEmployeeId(id, Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Application> findByClient(Long id) {
        return applicationRepository.findByClientId(id, Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Application> findAll() {
        return applicationRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Application findById(Long id) {
        return applicationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Заявки с id " + id + " не существует"));
    }

    public Application createWithClient(Application application, Long clientId) {

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник с таким id не найден"));

        application.setClient(client);
        application.setEmail(client.getEmail());
        application.setName(client.getFullName());
        return create(application);
    }

    public Application create(Application application) {
        application.setStatus(ApplicationStatus.NEW);
        return applicationRepository.save(application);
    }

    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }

    @Transactional
    public Application update(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с таким ID не найдена"));

        application.setStatus(status);

        return applicationRepository.save(application);
    }

    @Transactional
    public Application assignEmployee(Long applicationId, Long employeeId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Заявка с таким id не найдена"));

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник с таким id не найден"));

        application.setEmployee(employee);
        return applicationRepository.save(application);
    }

    public List<Application> findAllByCreationDateRange(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null) {
            return applicationRepository.findByDateOfCreationBetween(from, to, Sort.by(Sort.Direction.ASC, "creation_date"));
        } else if (from != null) {
            return applicationRepository.findByDateOfCreationAfter(from, Sort.by(Sort.Direction.ASC, "creation_date"));
        } else if (to != null) {
            return applicationRepository.findByDateOfCreationBefore(to, Sort.by(Sort.Direction.ASC, "creation_date"));
        } else {
            return findAll();
        }
    }

}
