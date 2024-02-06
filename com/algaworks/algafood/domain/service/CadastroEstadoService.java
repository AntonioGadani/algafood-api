package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {	//aulas: 4.32, 5.5, 8.6, 8.10, 11.21

	private static final String MSG_ESTADO_EM_USO 
		= "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();	//aula 11.21; Libera todas as alterações pendentes no banco de dados
		} catch (EmptyResultDataAccessException e) {			//mensagem em EstadoNaoEncontradoException.java
			throw new EstadoNaoEncontradoException(estadoId);  //Se n.encontrar estado,lança exception
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(		//Se o estado estiver em uso, lança exception
				String.format(MSG_ESTADO_EM_USO, estadoId));	//mensagem(MSG):constante declarada acima
		}
	}

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
			.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
}