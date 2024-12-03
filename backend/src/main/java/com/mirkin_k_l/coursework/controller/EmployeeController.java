package com.mirkin_k_l.coursework.controller;

import com.mirkin_k_l.coursework.entity.employee.Employee;
import com.mirkin_k_l.coursework.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    public Employee findByEmail(@RequestParam String email) {
        return employeeService.findByEmail(email);
    }

    @PostMapping("/registration")
    public Employee registerEmployee(@RequestBody Employee employee) {
        return employeeService.registration(employee);
    }

    @PostMapping("/authorization")
    public Employee authorizeEmployee(@RequestBody Employee employee) {
        return employeeService.authorization(employee);
    }

    @DeleteMapping("/delete_employee")
    public void deleteStudent(@RequestParam String email) {
        employeeService.deleteEmployee(email);
    }

}
