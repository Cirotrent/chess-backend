package com.esempio.ChessTournamentOnline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.esempio.ChessTournamentOnline.security.CustomAccessDeniedHandler;
import com.esempio.ChessTournamentOnline.security.JwtFilter;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
	    http
	    	.cors(cors -> {})
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	        	    .requestMatchers("/auth/**").permitAll()
	        	    .requestMatchers("/admin/**").hasRole("ADMIN")
	        	    .requestMatchers("/organizer/**").hasAnyRole("ADMIN", "ORGANIZER")
	        	    .requestMatchers("/tornei/**").hasAnyRole("ADMIN", "ORGANIZER")
	        	    .requestMatchers("/play/**").hasRole("PLAYER")
	        	    .anyRequest().authenticated()
	        )
	        .exceptionHandling(ex -> ex
	                .accessDeniedHandler(accessDeniedHandler)
	            )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}