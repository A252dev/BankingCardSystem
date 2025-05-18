package com.cards.cards.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.cards.cards.services.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    // @Autowired
    // private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/register", "/login").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     User user0 = new User(
    //             "username",
    //             "{noop}password",
    //             Collections.emptyList());

    //     return new InMemoryUserDetailsManager(user0);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // @Bean
    // AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //     provider.setUserDetailsService(userDetailsServiceImpl);
    //     provider.setPasswordEncoder(passwordEncoder());
    //     return provider;
    // }

    // private PasswordEncoder passwordEncoder() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'passwordEncoder'");
    // }

    // @Bean
    // public AuthenticationManager
    // authenticationManager(AuthenticationConfiguration configuration) throws
    // Exception {
    // return configuration.getAuthenticationManager();
    // }
}
