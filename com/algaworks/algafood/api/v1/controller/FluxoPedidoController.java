package com.algaworks.algafood.api.v1.controller;
import com.algaworks.algafood.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/pedidos/{codigoPedido}",produces = MediaType.APPLICATION_JSON_VALUE) 
//antes:@RequestMapping(value = "/pedidos/{codigoPedido}")	//aula 20.14
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {//aulas 12.22, 12.23, 23.31, 27.3

	@Autowired
	private FluxoPedidoService fluxoPedido; //injetando classe de serviço prá chamar método confirmar
	
	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@Override
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
//  public void confirmar(@PathVariable String codigoPedido) {  //Long pedidoId) {
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {  //Long pedidoId) {
		fluxoPedido.confirmar(codigoPedido);
		return ResponseEntity.noContent().build();	//aula 19.22
	}
/*.....................................aula 12.23.......................................*/
	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@Override
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {    //Long pedidoId) {Long pedidoId) {
	    fluxoPedido.cancelar(codigoPedido);
	    return ResponseEntity.noContent().build();	//aula 19.22
	}

	@CheckSecurity.Pedidos.PodeGerenciarPedidos		//aula 23.31
	@Override
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {    //Long pedidoId) {Long pedidoId) {
	    fluxoPedido.entregar(codigoPedido);
	    return ResponseEntity.noContent().build();	//aula 19.22
	}
	/*...............................Fim aula 12.23..........................................*/
	
}