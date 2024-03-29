package com.algaworks.algafood.core.email;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {		//aula 15.3, 15.8, 15.9
	
	// Atribuimos FAKE como padrão
	// Isso evita o problema de enviar e-mails de verdade caso você esqueça
	// de definir a propriedade
	private Implementacao impl = Implementacao.FAKE;
@NotNull
private String remetente;

private Sandbox sandbox = new Sandbox();	//aula 15.9

public enum Implementacao {
	SMTP, FAKE, SANDBOX
}

@Getter
@Setter
public class Sandbox {		//aula 15.9
	
	private String destinatario;	
	}
}
