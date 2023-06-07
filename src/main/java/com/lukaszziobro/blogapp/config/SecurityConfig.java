package com.lukaszziobro.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    public SecurityConfig(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint) {
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .anyRequest().authenticated())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails testuser = User.builder()
                .username("testuser")
                .password(passwordEncoder().encode("testuser"))
                .roles("ADMIN")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(testuser, admin);

    };
}
