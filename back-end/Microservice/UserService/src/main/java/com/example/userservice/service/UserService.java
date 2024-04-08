package com.example.userservice.service;

import com.example.userservice.dao.UserRepository;
import com.example.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser == null) {
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
    }

    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
        return user;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public void emailVerified(String email) {
        userRepository.updateUserType(email, "NORMAL_USER");
    }
}
