package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {   //12.19, 12.25

	private static final long serialVersionUID = 1L;

/*	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format("Não existe um pedido com código %d", pedidoId));
	}...........................................................................aula 12.25...*/															

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe um pedido com código %s", codigoPedido));
	}
	
}