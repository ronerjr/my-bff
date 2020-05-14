package com.roner.bff.service;

import com.roner.bff.entity.User;
import com.roner.bff.exception.NotFoundException;
import com.roner.bff.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User insertUser(User user) throws Exception {
        User userFromDB = userRepository.save(user);
        if (userFromDB == null) {
            throw new Exception("User was not persisted");
        }
        return userFromDB;
    }

    public User updateUser(UUID id, User user) throws Exception {
        Optional<User> userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            user.setId(userFromDB.get().getId());
            if (null == userRepository.save(user)) {
                throw new Exception("Failed to persist data");
            }
            return user;
        }
        throw new NotFoundException("No users found with id " + id);
    }

    public void deleteUser(UUID id) throws Exception {
        Optional<User> userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            userRepository.delete(userFromDB.get());
        } else {
            throw new NotFoundException("No users found with id " + id);
        }
    }

    public User getUserById(UUID id) throws NotFoundException {
        Optional<User> userFromDB = userRepository.findById(id);
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        } else {
            throw new NotFoundException("No users found with id " + id);
        }
    }
}
