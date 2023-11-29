package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {	//aula 14.8, 14.9, 14.10. 14.11
	InputStream recuperar(String nomeArquivo);	//aula 14.11
	void armazenar(NovaFoto novaFoto);			//aula 14.9
	void remover(String nomeArquivo);			//aula 14.10
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {	//aula 14.10
		this.armazenar(novaFoto);		//armazenar foto no disco
		
		if (nomeArquivoAntigo != null) {		//Se tem uma foto com nome antigo
			this.remover(nomeArquivoAntigo);	//remover a foto do disco, eliminando assim, qualquer lixo
		}
	}
	
	default String gerarNomeArquivo(String nomeOriginal) {		//aula 14.9
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}	
	
	@Builder
	@Getter
	class NovaFoto {
		
		private String nomeAquivo;
		private String contentType;
		private InputStream inputStream;	//inputStream=fluxo de leitura do qrquivo
		
	}
}