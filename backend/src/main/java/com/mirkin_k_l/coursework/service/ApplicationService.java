package com.mirkin_k_l.coursework.service;


import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import com.mirkin_k_l.coursework.entity.employee.Employee;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.repository.ApplicationRepository;
import com.mirkin_k_l.coursework.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Application> findByEmployee(Long id) {
        return applicationRepository.findByEmployee_Id(id);
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Application findById(Long id) {
        return applicationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Заявки с id " + id + " не существует"));
    }

    public Application create(Application application) {

        List<Employee> employees = employeeRepository.findAll();

        Employee leastLoadedEmployee = employees.stream()
                .min(Comparator.comparingLong(Employee::getCountApplications))
                .orElseThrow(() -> new RuntimeException("Нет доступных сотрудников"));

        application.setEmployee(leastLoadedEmployee);
        application.setStatus(ApplicationStatus.NEW);

        return applicationRepository.saveAndFlush(application);
    }

    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, ApplicationStatus status) {
        int updatedRows = applicationRepository.updateStatus(id, status);
        if (updatedRows == 0) {
            throw new NotFoundException("Заявки с id " + id + " не существует");
        }
    }
}
