package com.mirkin_k_l.coursework.controller;

import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import com.mirkin_k_l.coursework.repository.ApplicationRepository;
import com.mirkin_k_l.coursework.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/create")
    public Application createApplication(@RequestBody Application application) {
        return applicationService.create(application);
    }

    @PutMapping("/update")
    public void updateApplication(@RequestParam Long id, @RequestParam ApplicationStatus status){
        applicationService.update(id, status);
    }

    @GetMapping("/employee")
    public List<Application> findAllEmployeeApplications(@RequestParam Long id) {
        return applicationService.findByEmployee(id);
    }

    @GetMapping("/get")
    public List<Application> findAllApplications() {
        return applicationService.findAll();
    }

    @DeleteMapping("/delete")
    public void deleteApplication(@RequestParam Long id) {
        applicationService.delete(id);
    }

}
