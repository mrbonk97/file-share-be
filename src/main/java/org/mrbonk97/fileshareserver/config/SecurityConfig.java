package org.mrbonk97.fileshareserver.config;

import lombok.RequiredArgsConstructor;
import org.mrbonk97.fileshareserver.oauth2.CustomOAuth2SuccessHandler;
import org.mrbonk97.fileshareserver.oauth2.CustomOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.oauth2Login(o ->
                o
                        .authorizationEndpoint(point -> point.baseUri("/oauth2/authorization"))
                        .userInfoEndpoint(point -> point.userService(customOauth2UserService))
                        .redirectionEndpoint(point -> point.baseUri("/oauth2/callback/*"))
                        .successHandler(customOAuth2SuccessHandler)
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
