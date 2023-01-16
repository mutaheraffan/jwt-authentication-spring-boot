package com.authenticationusingjwt.controllers;

import com.authenticationusingjwt.helper.UserNotFoundException;
import com.authenticationusingjwt.models.Role;
import com.authenticationusingjwt.models.User;
import com.authenticationusingjwt.models.UserRole;
import com.authenticationusingjwt.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@SecurityRequirement(name = "Bearer")
@RequestMapping("/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // get all users
    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got all Users"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/get-all-users")
    public List<User> getAllUsers() {
        logger.info("Calling getAllUsers()");
        return this.userService.getAllUsers();
    }

    // get user by username
    @Operation(summary = "Get user with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got User with specified id"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        logger.info("Calling getUser()");
        return this.userService.getUser(username);
    }


    // create user
    @Operation(summary = "Creates a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created User"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) throws Exception {
        logger.info("Calling createUser()");

        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRole_id(50L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);

        return this.userService.createUser(user, roles);

    }

    // delete user by id
    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted User"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        logger.info("Calling deleteUser()");
        this.userService.deleteUser(userId);
    }

    // update user by username
    @Operation(summary = "Updates User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated User"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PutMapping("/update-user")
    public User updateUser(@RequestBody User user) throws Exception {
        logger.info("Calling updateUser()");
        return this.userService.updateUser(user);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(UserNotFoundException userNotFoundException) {
        return ResponseEntity.ok(userNotFoundException);
    }

}
