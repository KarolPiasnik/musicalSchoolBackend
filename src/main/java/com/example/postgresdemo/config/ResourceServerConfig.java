package com.example.postgresdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * The @EnableResourceServer annotation adds a filter of type OAuth2AuthenticationProcessingFilter automatically
 * to the Spring Security filter chain.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/api/students/**").authenticated()
                .antMatchers("/api/lessons/**").authenticated()
                .antMatchers("/api/gradebooks/**").authenticated()
                .antMatchers("/api/teachers/**").authenticated()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/remarks/**").authenticated()
                .antMatchers("/api/grades/**").authenticated()
                .antMatchers("/api/announcements/**").authenticated()
                .antMatchers("/api/subjects/**").authenticated()
                .antMatchers("/api/questions/**").authenticated()
                .antMatchers("/api/answers/**").authenticated()
                .antMatchers("/api/messages/**").authenticated()
                .antMatchers("/oauth/token/**").permitAll()
                .antMatchers("/**").authenticated();

    }
}
