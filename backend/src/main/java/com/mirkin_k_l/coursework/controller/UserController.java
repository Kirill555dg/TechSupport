package com.mirkin_k_l.coursework.controller;

import com.mirkin_k_l.coursework.entity.application.Application;
import com.mirkin_k_l.coursework.entity.user.User;
import com.mirkin_k_l.coursework.entity.user.UserRole;
import com.mirkin_k_l.coursework.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/profile")
    public User getUserProfile(@AuthenticationPrincipal String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/assigned-applications")
    public List<Application> getEmployeeApplications(@AuthenticationPrincipal String email) {
        return userService.findUserAssignedApplications(email);
    }

    @GetMapping("/submitted-applications")
    public List<Application> getClientApplications(@AuthenticationPrincipal String email) {
        return userService.findUserSubmittedApplications(email);
    }

    @PostMapping("/set-role/{id}")
    public ResponseEntity<User> setRole(@PathVariable Long id, @RequestBody UserRole role) {
        log.debug("ID: {}", id);
        log.debug("ROLE: {}", role);
        User user = userService.setRole(id, role);
        return ResponseEntity.ok(user);
    }
}
