package com.example.gardeningPlanner;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        permitH2Console(http);

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/*", "/registrierung", "/images/*", "/error").permitAll()
                .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/anmeldung")
                        .defaultSuccessUrl("/kalender", true)
                        .failureUrl("/annmeldung?error=true")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/abmelden")
                        .logoutSuccessUrl("/anmeldung?abmelden=true")
                        .permitAll());

        return http.build();
    }

    /*
     * Only for Development, remove afterwards
     */
    private static void permitH2Console(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.requestMatchers(PathRequest.toH2Console()).permitAll());
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
    }
}