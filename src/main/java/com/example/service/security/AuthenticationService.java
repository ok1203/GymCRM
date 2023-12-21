package com.example.service.security;

import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    public String authenticate(String username, String password) {
        if (loginAttemptService.isBlocked(username)) {
            throw new RuntimeException("User is locked out");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            loginAttemptService.loginSucceeded(username);
            UserDetails user = userDetailsService.loadUserByUsername(username);
            String token = jwtService.generateToken(user);
            return token;
        } catch (AuthenticationException e) {
            loginAttemptService.loginFailed(username);
            throw new RuntimeException("Invalid credentials");
        }
    }
}
