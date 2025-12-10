package com.ecom.organic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${ADMIN_USERNAME:user}")
        private String adminUsername;

        @Value("${ADMIN_PASSWORD:password}")
        private String adminPassword;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/index.html", "/login.html", "/style.css",
                                                                "/script.js", "/favicon.ico")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/products/**",
                                                                "/api/product/*/image")
                                                .permitAll()
                                                .requestMatchers("/admin.html", "/admin.js", "/add-product.html",
                                                                "/edit-product.html", "/product-form.js")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.POST, "/api/product").authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/product/**").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/product/**").authenticated()
                                                .anyRequest().authenticated())
                                .httpBasic(Customizer.withDefaults()) // Enable Basic Auth
                                .formLogin(form -> form
                                                .loginPage("/login.html") // Custom login page
                                                .loginProcessingUrl("/login") // Submit URL
                                                .defaultSuccessUrl("/admin.html", true) // Redirect to admin after login
                                                .failureUrl("/login.html?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/index.html")
                                                .permitAll());

                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.builder()
                                .username(adminUsername)
                                .password("{noop}" + adminPassword)
                                .roles("USER")
                                .build();
                return new InMemoryUserDetailsManager(user);
        }
}
