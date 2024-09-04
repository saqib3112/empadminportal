package com.officeportal.empadminportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.officeportal.empadminportal.repository.UserRepo;
import com.officeportal.empadminportal.service.UserTokenService;

@Configuration
@EnableWebSecurity
public class MyConfig {

	@Autowired
	private UserTokenService userTokenService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() 
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and()
            .authorizeHttpRequests()
                .requestMatchers("/api/login", "/api/registration").permitAll() 
                .anyRequest().authenticated() 
                .and()
            .addFilterBefore(new TokenAuthenticationFilter(userTokenService), UsernamePasswordAuthenticationFilter.class); 
        return http.build();
	}
}