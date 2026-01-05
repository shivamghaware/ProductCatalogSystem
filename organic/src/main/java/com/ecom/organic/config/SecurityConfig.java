package com.ecom.organic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private CustomAuthenticationSuccessHandler successHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/index.html", "/login.html", "/register.html",
                                                                "/style.css",
                                                                "/script.js", "/favicon.ico", "/api/auth/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/products/**",
                                                                "/api/product/*/image")
                                                .permitAll()
                                                .requestMatchers("/admin.html", "/admin.js", "/add-product.html",
                                                                "/edit-product.html", "/product-form.js")
                                                .hasAnyRole("ADMIN", "SELLER") // ADMIN and SELLER can access admin
                                                                               // pages
                                                .requestMatchers(HttpMethod.POST, "/api/product")
                                                .hasAnyRole("ADMIN", "SELLER")
                                                .requestMatchers(HttpMethod.PUT, "/api/product/**")
                                                .hasAnyRole("ADMIN", "SELLER")
                                                .requestMatchers(HttpMethod.DELETE, "/api/product/**")
                                                .hasAnyRole("ADMIN", "SELLER")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login.html") // Custom login page
                                                .loginProcessingUrl("/login") // Submit URL
                                                .successHandler(successHandler) // Use custom success handler
                                                .failureUrl("/login.html?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/index.html")
                                                .permitAll());

                return http.build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
