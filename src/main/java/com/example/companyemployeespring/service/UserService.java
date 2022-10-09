package com.example.companyemployeespring.service;

import com.example.companyemployeespring.entity.User;
import com.example.companyemployeespring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void saveUserWithPass(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        mailService.sendEmail(user.getEmail(), "welcome", "Hi " + user.getName() + " \n"
        + "you have successfully registred!!!");
    }



}
