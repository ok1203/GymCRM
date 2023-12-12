package com.example.service.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginAttemptService {
    private final Map<String, Integer> attempts = new HashMap<>();
    private final int MAX_ATTEMPTS = 3;
    private final int LOCKOUT_DURATION_MINUTES = 5;

    public void loginFailed(String username) {
        attempts.merge(username, 1, Integer::sum);
    }

    public boolean isBlocked(String username) {
        return attempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }

    public void resetAttempts(String username) {
        attempts.remove(username);
    }

}
