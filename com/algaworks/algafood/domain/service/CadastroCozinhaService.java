package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service		//@Service; também é um @Component, mais indicada p/classe de serviço
public class CadastroCozinhaService {	//aulas 4.27, 4.28, 5.4, 8.5, 8.11, 11.1, 11.21
	private static final String MSG_COZINHA_EM_USO 
											= "Cozinha de código %d não pode ser removida, pois está em uso";
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();	//aula 11.21; Libera todas as alterações pendentes no banco de dados
			
		} catch (EmptyResultDataAccessException e) {	//aula 8.5
			throw new CozinhaNaoEncontradaException(cozinhaId);	//Se não encontrar cozinha, lança exception
		
		} catch (DataIntegrityViolationException e) {	//aula 8.5
			throw new EntidadeEmUsoException(	//Se a cozinha estiver em uso, lança exception
				String.format(MSG_COZINHA_EM_USO, cozinhaId));//mensagem(MSG):constante declarada acima
		}						
	}
	public Cozinha buscarOuFalhar(Long cozinhaId) {	//aula 8.5>>//busca a entidade Cozinha
		return cozinhaRepository.findById(cozinhaId)	//Se encontrar, informa o id
			.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));//Se não encontrar, lança uma exception
	}
	
}	
