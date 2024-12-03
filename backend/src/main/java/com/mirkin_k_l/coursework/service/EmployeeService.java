package com.mirkin_k_l.coursework.service;


import com.mirkin_k_l.coursework.entity.employee.Employee;
import com.mirkin_k_l.coursework.exception.AlreadyExistException;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.exception.WrongPasswordException;
import com.mirkin_k_l.coursework.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee registration(Employee employee) {
        String email = employee.getEmail();
        if (employeeRepository.existsByEmail(email)) {
            throw new AlreadyExistException("Сотрудник с почтой " + email +" уже существует");
        }
        return employeeRepository.save(employee);
    }

    public Employee authorization(Employee employee) {
        String email = employee.getEmail();
        String password = employee.getPassword();
        Employee real = findByEmail(email);

        if (!real.getPassword().equals(password)) {
            throw new WrongPasswordException("Вы ввели неверный пароль");
        }
        return real;
    }

    public Employee findByEmail(String email) {
        return employeeRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Сотрудника с почтой " + email + " не существует"));
    }

    public void deleteEmployee(String email) {
        employeeRepository.deleteByEmail(email);
    }
}
