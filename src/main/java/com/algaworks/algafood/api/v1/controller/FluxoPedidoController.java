package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigoPedido}") 
//antes:@RequestMapping(value = "/pedidos/{codigoPedido}")	//aula 20.14
public class FluxoPedidoController {		//aulas 12.22, 12.23, 23.31

	@Autowired
	private FluxoPedidoService fluxoPedido; //injetando classe de serviço prá chamar método confirmar
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//  public void confirmar(@PathVariable String codigoPedido) {  //Long pedidoId) {
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {  //Long pedidoId) {
		fluxoPedido.confirmar(codigoPedido);
		return ResponseEntity.noContent().build();	//aula 19.22
	}
/*.....................................aula 12.23.......................................*/
	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {    //Long pedidoId) {Long pedidoId) {
	    fluxoPedido.cancelar(codigoPedido);
	    return ResponseEntity.noContent().build();	//aula 19.22
	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {    //Long pedidoId) {Long pedidoId) {
	    fluxoPedido.entregar(codigoPedido);
	    return ResponseEntity.noContent().build();	//aula 19.22
	}
	/*...............................Fim aula 12.23..........................................*/
	
}