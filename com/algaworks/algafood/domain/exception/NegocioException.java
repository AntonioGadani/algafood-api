package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}
	//abaixo, Throwabl: classe pai de todas as exceções
	public NegocioException(String mensagem, Throwable causa) { //8.8	8.10.
		super(mensagem, causa);
	}
}