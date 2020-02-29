package com.web.hackathon.security;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@AllArgsConstructor
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;
    private final List<String> excludePatterns;

    @Override
    public void configure(final HttpSecurity http) {
        final JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, excludePatterns);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
