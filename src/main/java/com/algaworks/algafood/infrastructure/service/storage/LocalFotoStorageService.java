package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

//@Service	comentado na aula 14.21. Anotação será usada na implementação AmazonS3FotoStorageService
public class LocalFotoStorageService implements FotoStorageService {	//aula 14.8, 14.10, 14.11, 14.20, 14.21
		/* @Value("${algafood.storage.local.diretorio-fotos}") 
		private Path diretorioFotos; //injetando propriedade inserida em application.properties, coment:aula 14.20*/
	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public InputStream recuperar(String nomeArquivo) {	//aula 14.11
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);

			return Files.newInputStream(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar arquivo.", e);
		}
	}
	
	@Override
	public void armazenar(NovaFoto novaFoto) {		//caminho pelo método Path getArquivoPath, abaixo
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeAquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar arquivo.", e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível excluir arquivo.", e);
		}
	}

	private Path getArquivoPath(String nomeArquivo) {
		// return diretorioFotos.resolve(Path.of(nomeArquivo)); coment: aula 14.20
		return storageProperties.getLocal().getDiretorioFotos()	//aula 14.20
				.resolve(Path.of(nomeArquivo));
	}

}