package com.ecom.organic.config;

import com.ecom.organic.model.User;
import com.ecom.organic.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME:admin}") // Default admin username
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:password}") // Default admin password
    private String adminPassword;

    @Value("${ADMIN_EMAIL:admin@example.com}")
    private String adminEmail;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin exists
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            // In a real app, maybe don't hardcode, but here we use config
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setEmail(adminEmail);
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);
            System.out.println("Admin user created: " + adminUsername);
        }

        // Check if seller exists
        if (userRepository.findByUsername("seller").isEmpty()) {
            User seller = new User();
            seller.setUsername("seller");
            seller.setPassword(passwordEncoder.encode("password")); // Default password
            seller.setEmail("seller@example.com");
            seller.setRole("ROLE_SELLER");

            userRepository.save(seller);
            System.out.println("Seller user created: seller");
        }
    }
}
