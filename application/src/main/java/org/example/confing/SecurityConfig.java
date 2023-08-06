package org.example.confing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/producto/**").hasAnyRole("ADMIN", "CUSTOMER") // Allow access to URLs starting with /public // Allow access to URLs starting with /public/
                .antMatchers(HttpMethod.GET, "/file/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }
    @Bean
    public UserDetailsService memoryUsers() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails costumer = User.builder()
                .username("costumer")
                .password(passwordEncoder().encode("costumer123"))
                .roles("COSTUMER")
                .build();

        return new InMemoryUserDetailsManager(admin, costumer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
