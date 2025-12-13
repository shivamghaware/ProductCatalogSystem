package com.ecom.organic.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads environment variables from .env file into Spring's environment
 * This runs before the application context is fully initialized
 */
public class DotenvConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            // Load .env file from the root of the project
            Dotenv dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir"))
                    .ignoreIfMissing() // Don't fail if .env is missing (useful for production)
                    .load();

            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            Map<String, Object> envMap = new HashMap<>();

            // Add all .env variables to Spring environment
            dotenv.entries().forEach(entry -> {
                envMap.put(entry.getKey(), entry.getValue());
            });
            System.out.println("Successfully loaded .env file");

            environment.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", envMap));
        } catch (Exception e) {
            // Log warning but don't fail - allows running without .env file
            System.err.println("Warning: Could not load .env file: " + e.getMessage());
        }
    }
}
