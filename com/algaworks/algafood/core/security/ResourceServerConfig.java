package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ResourceServerConfig {	//aula 27.6, 27.10, 27.11

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/oauth2/**").authenticated()
            .and()
            .csrf().disable()
            .cors().and()
            //.oauth2ResourceServer().jwt();	//aula 27.10
            .oauth2ResourceServer().jwt();
        	//return http.build();	//aula 27.11
        	return http.formLogin(Customizer.withDefaults()).build();
        	 //return http.formLogin(customizer -> customizer.loginPage("/login")).build();
    }

}