package com.SpringbootProject.ProjectManagementTool.Configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/**").authenticated().anyRequest().permitAll()
                        )

                .addFilterBefore(new jwtTokenAuthenticator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return  http.build();
    }


//    private CorsConfigurationSource  corsConfiguration() {
//        return new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                CorsConfiguration cfg = new CorsConfiguration();
//                cfg.setAllowedOrigins(Arrays.asList(
//                        "http://localhost:3000",
//                        "http://localhost:5173",
//                        "http://localhost:4200"
//                ));
//
//                cfg.setAllowCredentials(true);
//                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                cfg.setAllowedHeaders(Collections.singletonList("*"));
//                cfg.setExposedHeaders(Arrays.asList("Authorization"));
//                cfg.setMaxAge(3600L);
//                return cfg;
//            }
//        };
//    }
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:5173",
            "http://localhost:4200"
    ));
    configuration.setAllowCredentials(true); // Allow credentials (JWT cookies or headers)
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH")); // HTTP methods
    configuration.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
    configuration.setExposedHeaders(Arrays.asList("Authorization")); // Expose the Authorization header
    configuration.setMaxAge(3600L); // Cache the preflight response

    // Apply this configuration to all endpoints
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
