package com.example.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyUniquePrometheusConfig  {

    @Bean
    public CollectorRegistry collectorRegistry() {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    public Counter customCounter(CollectorRegistry collectorRegistry) {
        return Counter.build()
                .name("custom_counter")
                .help("This is a custom counter metric, it counts how much trainee delete operations were performed")
                .register(collectorRegistry);
    }
}
