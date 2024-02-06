package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {			//aula 14.6, 14.7, 14.9, 14.12, 14.15

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;    //aula 14.9
	/*..........................................................praticado e comentado: aula 14.6	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		return produtoRepository.save(foto);
	}
..........................................................praticado e comentado: aula 14.6......*/
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {	//aula 14.7, 14.9(InputStream)
		Long restauranteId = foto.getRestauranteId();	//método em FotoProduto pega o id do restaurante
		Long produtoId = foto.getProduto().getId();		//id da foto é o mesmo do produto, do restaurante
		String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());	/*aula 14.9:Gerando
		 novo nome de arquivo,  em FotoStorageService........................*/
		String nomeArquivoExistente = null;	//aula 14.10
		//abaixo: Optional declara se tem ou não tem Uma foto	
		Optional<FotoProduto> fotoExistente = produtoRepository	//Tem ou não uma foto do produto no Optional
				.findFotoById(restauranteId, produtoId);
		
		if (fotoExistente.isPresent()) {	//Tem uma foto? Então tem que excluir
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();//pega o nome do arquivo da foto no disco
			produtoRepository.delete(fotoExistente.get());
			//acima: deletar(ProdutoRepositoryQueries), implementado em ProdutoRepositoryImpl
		}
		//return produtoRepository.save(foto);		coment: aula 14.9
		foto.setNomeArquivo(nomeNovoArquivo);	//aula 14.9: nome novo do arquivo usando UuId
		foto =  produtoRepository.save(foto);	//primeiro salva os dados da foto no Banco de dados(com repositório)
		produtoRepository.flush();	 /*flush, para o JPA descarregar tudo que tiver na fila. Se acontecer algum
		problema, acontece um rollback antes de aramazenar a foto em disco  */
		
		NovaFoto novaFoto = NovaFoto.builder()		//chama novaFoto usando o construtor de objetos
				.nomeAquivo(foto.getNomeArquivo())	//pega o nome do arquivo, já usando UuId
				.contentType(foto.getContentType())	//aula 14.23
				.inputStream(dadosArquivo)	//usa o fluxo de dados(inputStream)prá pegar os dados do arquivo
				.build();
		//fotoStorage.armazenar(novaFoto);   coment: aula 14.10
		fotoStorage.substituir(nomeArquivoExistente, novaFoto);	//aula 14.10
		
		return foto;
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {	//classe 14.12
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
	}

	@Transactional
	public void excluir(Long restauranteId, Long produtoId) {	//aula 14.15
		FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);
		
		produtoRepository.delete(foto);
		produtoRepository.flush();

		fotoStorage.remover(foto.getNomeArquivo());
	}
	
}