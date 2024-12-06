package com.mirkin_k_l.coursework.controller;

import com.mirkin_k_l.coursework.entity.employee.User;
import com.mirkin_k_l.coursework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/employee")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/registration")
    public User registerEmployee(@RequestBody User employee) {
        return userService.registration(employee);
    }

    @PostMapping("/authorization")
    public User authorizeEmployee(@RequestBody User employee) {
        return userService.authorization(employee);
    }

    @DeleteMapping("/delete_employee")
    public void deleteStudent(@RequestParam String email) {
        userService.deleteUser(email);
    }

}
