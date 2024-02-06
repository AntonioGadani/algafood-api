package com.algaworks.algafood.domain.service;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
public interface EnvioEmailService { 		//aula 15.3, 15.4, 15.5

	void enviar(Mensagem mensagem);
	@Getter
	@Builder
	class Mensagem {
		@Singular		//aula 15.4
		private Set<String> destinatarios;
	
		@NonNull		//para validação, caso o assunto não seja informado
		private String assunto;
		@NonNull
		private String corpo;
		
		@Singular("variavel")			//aula 15.5
		private Map<String, Object> variaveis;
		}
}

