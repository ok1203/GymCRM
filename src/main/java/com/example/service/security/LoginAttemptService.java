package com.example.service.security;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LoginAttemptService {
    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();
    private final Map<String, Date> lockedUsers = new ConcurrentHashMap<>();
    private final int MAX_ATTEMPTS = 3;
    private final int LOCKOUT_DURATION_MINUTES = 5;

    public LoginAttemptService() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(this::clearExpiredLocks, 1, 1, TimeUnit.MINUTES);
    }

    public void loginSucceeded(String username) {
        attempts.remove(username);
    }

    public void loginFailed(String username) {
        int attemptsOfOne = attempts.getOrDefault(username, 0);
        attemptsOfOne++;
        attempts.put(username, attemptsOfOne);
        if (attemptsOfOne >= MAX_ATTEMPTS) {
            lockUser(username);
        }
    }

    public boolean isBlocked(String username) {
        return lockedUsers.containsKey(username);
    }

    private void lockUser(String username) {
        Date lockExpiry = new Date(System.currentTimeMillis() + (LOCKOUT_DURATION_MINUTES * 60 * 1000));
        lockedUsers.put(username, lockExpiry);
    }

    private void clearExpiredLocks() {
        Date currentTime = new Date();
        lockedUsers.entrySet().removeIf(entry -> entry.getValue().before(currentTime));
    }

}
