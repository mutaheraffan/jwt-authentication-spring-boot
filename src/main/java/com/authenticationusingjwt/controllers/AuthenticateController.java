package com.authenticationusingjwt.controllers;

import com.authenticationusingjwt.config.JwtUtils;
import com.authenticationusingjwt.models.JwtRequest;
import com.authenticationusingjwt.models.JwtResponse;
import com.authenticationusingjwt.services.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthenticateController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    // generate token

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        logger.info("Calling generateToken()");
        try
        {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        }
        catch(UsernameNotFoundException e)
        {
            e.printStackTrace();
            throw new Exception("User not found");
        }

        //  getting authenticated user

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = this.jwtUtils.generateToken(userDetails);
        String expiration_date = this.jwtUtils.extractExpiration(token).toString();
        return  ResponseEntity.ok(new JwtResponse(token, expiration_date));

    }

    private void authenticate(String username, String password) throws Exception {

        try {

           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        } catch (DisabledException e) {
            throw new Exception("USER DISABLED" + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials " + e.getMessage());
        }
    }
}
