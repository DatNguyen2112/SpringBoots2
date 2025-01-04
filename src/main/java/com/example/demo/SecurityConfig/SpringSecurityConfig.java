package com.example.demo.SecurityConfig;

import com.example.demo.Until.JwtAuthenticationEntryPoint;
import com.example.demo.Until.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {
    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SpringSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                      authorize.requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("ADMIN");
                    authorize.requestMatchers("/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                }));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
