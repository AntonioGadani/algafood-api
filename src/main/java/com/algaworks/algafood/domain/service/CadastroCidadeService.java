package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {	//aulas:  4.32, 5.5, 8.6, 8.11, 11.21

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();		//dentro da Entidade Cidade, pega o id do Estado
		//abaixo, na classe Estado, verifica se o estado existe usando o método buscarOuFalhar
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);	
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();	//aula 11.21; Libera todas as alterações pendentes no banco de dados
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);		//Se não encontrar cidade, lança exception

		
		} catch (DataIntegrityViolationException e) {	//Se a cidade estiver em uso, lança exception
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	public Cidade buscarOuFalhar(Long cidadeId) {	//busca a entidade Cidade
		return cidadeRepository.findById(cidadeId)	//Se encontrar, informa o id
			.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));	//Se não encontrar, lança uma exception
	}
	
}