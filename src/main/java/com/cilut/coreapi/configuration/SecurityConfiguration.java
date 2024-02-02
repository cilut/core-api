package com.cilut.coreapi.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    private static final String[] PUBLIC_URL = {
            "/server/**",
            "/core-api/dev/**",
            "/core-api/api/users/save/**",
            "/core-api/api/auth/generate-token",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println(issuerUri);
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(PUBLIC_URL).permitAll()
                                .anyRequest().authenticated()
                        //.requestMatchers("/core-api/api/users/admin/**").hasAuthority("ADMIN")
                        //.requestMatchers("/core-api/api/users/**").hasAuthority("NORMAL")
                        //.anyRequest().hasAuthority("NORMAL")
                )
                .oauth2ResourceServer(c -> c.jwt(op -> op.decoder(JwtDecoders.fromIssuerLocation(issuerUri))))
                .build();
    }

}
