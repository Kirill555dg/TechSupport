package com.mirkin_k_l.coursework.service;


import com.mirkin_k_l.coursework.entity.employee.User;
import com.mirkin_k_l.coursework.exception.AlreadyExistException;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.exception.WrongPasswordException;
import com.mirkin_k_l.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registration(User employee) {
        String email = employee.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistException("Сотрудник с почтой " + email +" уже существует");
        }
        return userRepository.save(employee);
    }

    public User authorization(User employee) {
        String email = employee.getEmail();
        String password = employee.getPassword();
        User real = findByEmail(email);

        if (!real.getPassword().equals(password)) {
            throw new WrongPasswordException("Вы ввели неверный пароль");
        }
        return real;
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Сотрудника с почтой " + email + " не существует"));
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }
}
