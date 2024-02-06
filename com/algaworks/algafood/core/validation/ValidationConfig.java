package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {	//aula 9.14
//configurando o projeto para que o resource bundle do Bean validations seja o mesmo
								 // resource bundle(pacote de recursos do Spring
//só usaremos o arquivo (src/main/resources/messages.properties. Não mais o validationMessages
	@Bean
	LocalValidatorFactoryBean validator(MessageSource messageSource) {	//public parece não ser mais necessário
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource);
		return bean;
	}
	
}