package com.authenticationusingjwt.services.impl;

import com.authenticationusingjwt.models.User;
import com.authenticationusingjwt.models.UserRole;
import com.authenticationusingjwt.repository.RoleRepository;
import com.authenticationusingjwt.repository.UserRepository;
import com.authenticationusingjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if (local != null) {
            System.out.println("User is already there!");
            throw new Exception("User already present");
        } else {
            //create user
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }

        return local;

    }

    // getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    // getting all users
    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // updating user
    @Override
    public User updateUser(User updatedUser) throws Exception {
        User user = this.userRepository.getReferenceById(updatedUser.getUser_id());
        System.out.println(user.getUsername());
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEnabled(updatedUser.isEnabled());
        } else {
            System.out.println("User is not registered with user id: " + updatedUser.getUser_id());
            throw new Exception("User is not registered with user id: " + updatedUser.getUser_id());
        }

        User updatedUserData = this.userRepository.save(user);
        return updatedUserData;

    }

    // deleting user
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
