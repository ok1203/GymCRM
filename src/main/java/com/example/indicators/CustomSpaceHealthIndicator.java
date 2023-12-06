package com.example.indicators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CustomSpaceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isHealthy = checkDiskSpace();

        if (isHealthy) {
            return Health.up().withDetail("message", "Everything is fine!").build();
        } else {
            return Health.down().withDetail("message", "Something went wrong!").build();
        }
    }


    private boolean checkDiskSpace() {
        // Check available disk space, assuming a threshold of 10GB for demonstration
        File diskPartition = new File("/");
        long freeSpace = diskPartition.getFreeSpace() / (1024 * 1024 * 1024); // Convert to GB

        return freeSpace >= 10; // Health check passes if there's at least 10GB free
    }
}
