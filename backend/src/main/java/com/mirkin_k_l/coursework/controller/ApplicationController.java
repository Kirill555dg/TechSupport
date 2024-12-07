package com.mirkin_k_l.coursework.controller;

import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.application.ApplicationStatus;
import com.mirkin_k_l.coursework.repository.ApplicationRepository;
import com.mirkin_k_l.coursework.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<Application> createApplication(
            @RequestBody Application application,
            @AuthenticationPrincipal String email) {
        log.debug(String.valueOf(email));
        if (!email.equals("anonymousUser")) {
            application = applicationService.createWithClient(application, email);
        } else {
            application = applicationService.create(application);
        }
        log.debug("Applications {}", application);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Application> updateApplication(
            @PathVariable Long id,
            @RequestBody Map<String, String> body){

        ApplicationStatus status = ApplicationStatus.valueOf(body.get("status"));
        Application application = applicationService.update(id, status);
        log.debug("Applications {}", application);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Application>> findAllEmployeeApplications(@AuthenticationPrincipal String email) {
        log.debug("EMAIL: {}", email);
        List<Application> applications = applicationService.findByEmployee(email);
        log.debug("Applications {}", applications);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/client")
    public ResponseEntity<List<Application>> findAllClientApplications(@AuthenticationPrincipal String email) {
        List<Application> applications = applicationService.findByClient(email);
        log.debug("Applications {}", applications);
        return ResponseEntity.ok(applications);
    }

    @GetMapping
    public ResponseEntity<List<Application>> findAllApplications(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {

        return ResponseEntity.ok(applicationService.findAllByCreationDateRange(from, to));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> findApplication(@PathVariable Long id) {
        Application application = applicationService.findById(id);

        log.debug("Application {}", application);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Application> assignEmployee(@PathVariable Long id,
                                                      @AuthenticationPrincipal String email) {
        Application application = applicationService.assignEmployee(id, email);
        log.debug("Application {}", application);
        return ResponseEntity.ok(application);
    }
}
