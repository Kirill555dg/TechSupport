package com.mirkin_k_l.coursework.service;


import com.mirkin_k_l.coursework.entity.user.User;
import com.mirkin_k_l.coursework.entity.user.UserRole;
import com.mirkin_k_l.coursework.exception.AlreadyExistException;
import com.mirkin_k_l.coursework.exception.NotFoundException;
import com.mirkin_k_l.coursework.exception.WrongPasswordException;
import com.mirkin_k_l.coursework.security.JwtUtil;
import com.mirkin_k_l.coursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        User realUser = findByEmail(email);
        log.debug(user.toString());
        log.debug(realUser.toString());
        if (!passwordEncoder.matches(password, realUser.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }

        return jwtUtil.generateToken(realUser.getEmail(), realUser.getRole().name());
    }

    public User registration(User user) {
        log.debug(user.toString());

        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistException("Сотрудник с почтой " + email +" уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.CLIENT);
        return userRepository.save(user);
    }

    public User authorization(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        User realUser = findByEmail(email);

        if (!passwordEncoder.matches(password, realUser.getPassword())) {
            throw new WrongPasswordException("Неверный пароль");
        }
        return realUser;
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Сотрудник с почтой " + email + " не найден"));
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public User setRole(Long id, UserRole userRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Сотрудник с id="+id+" не найден"));
        user.setRole(userRole);
        return userRepository.save(user);
    }

    public void createAdmin() {

        // Проверяем, существует ли уже пользователь с ролью admin
        User admin;
        try {
            admin = findByEmail("admin@email.com");
        } catch (NotFoundException e) {
            admin = new User();
            admin.setEmail("admin@email.com");
            admin.setFirstName("admin");
            admin.setLastName("admin");
        }
        admin.setPassword("admin");
        admin.setRole(UserRole.ADMIN); // Убедитесь, что роль ADMIN существует в вашем enum
        
        // Сохраняем администратора в базе данных
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepository.save(admin);
    }
}
