package com.algaworks.algafood.core.web;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class AlgaHalConfiguration {		//aula 20.10
	@Bean
	HalConfiguration globalPolicy() {		//antes tinha public 
		return new HalConfiguration()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON)
				.withMediaType(AlgaMediaTypes.V2_APPLICATION_JSON);	//aula 20.11
	}

}