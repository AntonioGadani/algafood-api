package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CryptConfig {		//aula 23.15

	@Bean
	PasswordEncoder passwordEncoder() {  //	"public "
		return new BCryptPasswordEncoder();
	}
	
}