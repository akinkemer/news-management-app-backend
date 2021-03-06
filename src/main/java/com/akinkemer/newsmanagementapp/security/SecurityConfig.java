package com.akinkemer.newsmanagementapp.security;

import com.akinkemer.newsmanagementapp.filter.CustomAuthenticationFilter;
import com.akinkemer.newsmanagementapp.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests().antMatchers("/api/v1/login/**", "/api/v1/token/refresh/**","/api/v1/user/save/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/ws/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/file/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/file/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
