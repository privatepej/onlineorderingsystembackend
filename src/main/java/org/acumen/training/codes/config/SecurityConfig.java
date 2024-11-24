package org.acumen.training.codes.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasAnyAuthority("ADMINISTRATOR")
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
	        .csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
            corsConfiguration.setAllowedMethods(Arrays.asList(
                    "GET", "POST", "PUT", "DELETE", "PATCH"
            ));
            corsConfiguration.setAllowedHeaders(Arrays.asList(
                    "Authorization", "Content-Type", 
                    "Access-Control-Allow-Credentials", 
                    "Access-Control-Allow-Headers", 
                    "Access-Control-Allow-Origin", 
                    "Access-Control-Allow-Methods"
            ));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
        };
    }

	
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
