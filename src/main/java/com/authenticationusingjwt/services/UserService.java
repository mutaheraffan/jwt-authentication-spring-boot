package com.authenticationusingjwt.services;

import com.authenticationusingjwt.models.User;
import com.authenticationusingjwt.models.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {

    // creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    // get user by username
    public User getUser(String username);

    // get all users
    public List<User> getAllUsers();

    // updating user
    public User updateUser(User updatedUser) throws Exception;

    // delete user by id
    public void deleteUser(Long userId);
}
