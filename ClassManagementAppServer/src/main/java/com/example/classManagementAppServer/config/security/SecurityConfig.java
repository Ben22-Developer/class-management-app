package com.example.classManagementAppServer.config.security;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain httpSecurity (HttpSecurity httpSecurity) {

        return httpSecurity
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.POST, "/all/test/post", "/all/free/api/confirm").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/all/password-reset-code").permitAll()
                        .requestMatchers
                                (HttpMethod.GET, "/secure/unsaved/api/validate", "/login", "/logout")
                        .permitAll()
                        .requestMatchers("/all/**", "/developer/**", "/css/**", "/images/**", "/js/**").permitAll() //
                        .requestMatchers("/secure/**").authenticated()
                        .requestMatchers("/admin/**", "/").hasRole("ADMIN"))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(form -> form.loginPage("/login"))
//                        .loginProcessingUrl("/api/login"))
                .logout(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
//                .headers(headers -> headers
//                        .contentSecurityPolicy(csp -> csp
//                                .policyDirectives("default-src 'self'; " +
//                                        "script-src 'self' 'unsafe-inline' https://kit.fontawesome.com; " +
//                                        "style-src 'self' 'unsafe-inline' https://ka-f.fontawesome.com https://fonts.googleapis.com; " +
//                                        "connect-src 'self' https://ka-f.fontawesome.com; " +
//                                        "font-src 'self' https://gstatic.com https://fonts.gstatic.com https://ka-f.fontawesome.com;"
//                                )
//
//                        )
//                )
                .build();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder (@Value("${spring.security.user.password.strength}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }
}
