package com.algaworks.algafood.domain.model;
import java.util.Arrays;
import java.util.List;

public enum StatusPedido {		//aula 7.12, 12.24, 19.23

	/*CRIADO("Criado"),
	CONFIRMADO("Confirmado"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado");	
*............variáveis de instância, ACIMA, alteradas conforme ABAIXO,  na aula 12.24...........*/
	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),//posso confrimar se: criado
	ENTREGUE("Entregue", CONFIRMADO), //posso entregar se: confirmado
	CANCELADO("Cancelado", CRIADO, CONFIRMADO); //posso cancelar se: criado ou confirmado
	
	private String descricao;
	private List<StatusPedido> statusAnteriores; //12.24: cada instância terá uma lista(statusAnteriores)
	
	StatusPedido(String descricao, StatusPedido...statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {//aula 12.24, exemplo abaixo
		return !novoStatus.statusAnteriores.contains(this);/*se novoStatus contém "Cancelado"...,
													CRIADO e CONFIRMADO são status anteriores*/ 
	}
	public boolean podeAlterarPara(StatusPedido novoStatus) {	//aula 19.23
		return !naoPodeAlterarPara(novoStatus);
	}
	
}