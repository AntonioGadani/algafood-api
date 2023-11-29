package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {	//aula 8.11
		super(mensagem);
	}
		public CidadeNaoEncontradaException(Long cidadeId) {
			this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
		}
		
	}