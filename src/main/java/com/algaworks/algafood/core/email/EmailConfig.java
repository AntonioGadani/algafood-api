package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {				//aula 15.8, 15.9

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	EnvioEmailService envioEmailService() {		//public parece desnecessário a partir do Spring 2.7.14
		// Acho melhor usar switch aqui do que if/else if
		switch (emailProperties.getImpl()) {
			case FAKE:
				return new FakeEnvioEmailService();
			case SMTP:
				return new SmtpEnvioEmailService();
			case SANDBOX:		//aula 15.9
				return new SandboxEnvioEmailService();
			default:
				return null;
		}
	}

}