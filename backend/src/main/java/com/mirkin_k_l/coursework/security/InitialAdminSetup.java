package com.mirkin_k_l.coursework.security;

import com.mirkin_k_l.coursework.entity.user.User;
import com.mirkin_k_l.coursework.entity.user.UserRole;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialAdminSetup implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitialAdminSetup(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.createAdmin();
        System.out.println("Admin user created!");
    }
}
