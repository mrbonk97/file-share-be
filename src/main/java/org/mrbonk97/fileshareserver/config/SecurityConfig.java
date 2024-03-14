package org.mrbonk97.fileshareserver.config;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.service.CustomOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOauth2UserService customOauth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        http.oauth2Login(o ->
                o.authorizationEndpoint(point -> point
                        .baseUri("/oauth2/authorization"))
                        .userInfoEndpoint(point -> point.userService(customOauth2UserService))
        );


        return http.build();
    }
}
