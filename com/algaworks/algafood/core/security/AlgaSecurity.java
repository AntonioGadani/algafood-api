package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {	//aula 27.3

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public boolean isAutenticado() {
		return getAuthentication().isAuthenticated();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		if (restauranteId == null) {
			return false;
		}
		
		return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
		return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean temEscopoEscrita() {
		return hasAuthority("SCOPE_WRITE");
	}
	
	public boolean temEscopoLeitura() {
		return hasAuthority("SCOPE_READ");
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return temEscopoEscrita() && (hasAuthority("GERENCIAR_PEDIDOS")
				|| gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean podeConsultarRestaurantes() {
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeGerenciarCadastroRestaurantes() {
		return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
		return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
				|| gerenciaRestaurante(restauranteId));
	}
	
	public boolean podeConsultarUsuariosGruposPermissoes() {
		return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeEditarUsuariosGruposPermissoes() {
		return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
		return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
				|| usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}

	public boolean podePesquisarPedidos() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarFormasPagamento() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCidades() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstados() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCozinhas() {
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstatisticas() {
		return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}
	
}
/*.....................................................código da classe reescrito na aula 27.3.....
package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {			//aula 23.17, 23.28, 23.30, 23.38, 23.39, 23.40
	
	@Autowired		//aula 23.28
	private RestauranteRepository restauranteRepository;
	
	@Autowired		//aula 23.30
	private PedidoRepository pedidoRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isAutenticado() {		//aula 23.40
		return getAuthentication().isAuthenticated();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
//	public boolean gerenciaRestaurante(Long restauranteId) {	//aula 23.28
//	return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
//	//acima: se exixte um usuário responsável, retorna i ID desse responsável
//}................................................................................aula 23.30
	
	public boolean gerenciaRestaurante(Long restauranteId) {		//aula 23.30
	    if (restauranteId == null) {
	        return false;
	    }	    
	    return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}	
	
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {	//aula 23.31
	    return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	} 

	public boolean usuarioAutenticadoIgual(Long usuarioId) {	//aula 23.38
		return getUsuarioId() != null && usuarioId != null
				&& getUsuarioId().equals(usuarioId);
	}
	
	//Dois métodos abaixo: À pesquisa de pedidos do usuário finalnão é exibido o link CONFIRMAR
	public boolean hasAuthority(String authorityName) {		//aula 23.39
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean temEscopoEscrita() {		//aula 23.40
		return hasAuthority("SCOPE_WRITE");
	}
	
	public boolean temEscopoLeitura() {		//aula 23.40
		return hasAuthority("SCOPE_READ");
	}

	public boolean podeGerenciarPedidos(String codigoPedido) {		//aula 23.40
		return temEscopoEscrita() && (hasAuthority("GERENCIAR_PEDIDOS")
				|| gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean podeConsultarRestaurantes() {		//aula 23.40
		return temEscopoLeitura() && isAutenticado();
	}
	
	public boolean podeGerenciarCadastroRestaurantes() {		//aula 23.40
		return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {		//aula 23.40
		return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
				|| gerenciaRestaurante(restauranteId));
	}
	
	public boolean podeConsultarUsuariosGruposPermissoes() {		//aula 23.40
		return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podeEditarUsuariosGruposPermissoes() {		//aula 23.40
		return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {		//aula 23.40
		return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS")
				|| usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}

	public boolean podePesquisarPedidos() {		//aula 23.40
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarFormasPagamento() {		//aula 23.40
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCidades() {		//aula 23.40
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstados() {		//aula 23.40
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarCozinhas() {		//aula 23.40
		return isAutenticado() && temEscopoLeitura();
	}
	
	public boolean podeConsultarEstatisticas() {		//aula 23.40
		return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}
	
}



//	public boolean podeGerenciarPedidos(String codigoPedido) {		//aula 23.39
//		return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
//			|| gerenciaRestauranteDoPedido(codigoPedido));
//	}
//....................................................................coment: aula 23.40 
	.....................................................código da classe reescrito na aula 27.3.....*/
