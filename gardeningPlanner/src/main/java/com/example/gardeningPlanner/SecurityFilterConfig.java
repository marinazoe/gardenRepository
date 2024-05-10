package com.example.gardeningPlanner;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        permitCustomLoginPage(http, "/login");
        permitH2Console(http);

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/css/styles.css").permitAll()
                .requestMatchers("/register").anonymous()
                .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated());
        return http.build();
    }

    private static void permitCustomLoginPage(HttpSecurity http, String url) throws Exception {
        http.formLogin(form -> form
                .loginPage(url)
                .failureHandler(customAuthenticationFailureHandler(url + "?error=true"))
                .permitAll());
    }

    private static AuthenticationFailureHandler customAuthenticationFailureHandler(String url) {
        return new SimpleUrlAuthenticationFailureHandler(url);
    }

    private static void permitH2Console(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.requestMatchers(PathRequest.toH2Console()).permitAll());
        http.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin));
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
    }
}