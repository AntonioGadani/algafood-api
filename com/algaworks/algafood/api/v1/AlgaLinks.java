package com.algaworks.algafood.api.v1;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
@Component
public class AlgaLinks {	/*aula 19.20, 19.21, 19.24, 19.25, 19.26, 19.27, 19.28, 19.29,  19.30,  19.31,  19.32
								   19.33, 19.34, 19.35, 19.37 */
	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
	//		public Link linkToPedidos() {		//aula 19.20, coment: aula 19.26
	public Link linkToPedidos(String rel) {	//aula 19.26
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl,
//				PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");  //coment: aula 19.26
				PAGINACAO_VARIABLES.concat(filtroVariables)), rel);	//aula 19.26
//		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();  //coment:aula 19.26
	}
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {	//aula 19.22
		return linkTo(methodOn(FluxoPedidoController.class)
				.confirmar(codigoPedido)).withRel(rel);
	}

	public Link linkToEntregaPedido(String codigoPedido, String rel) {	//aula 19.22
		return linkTo(methodOn(FluxoPedidoController.class)
				.entregar(codigoPedido)).withRel(rel);
	}

	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {	//aula 19.22
		return linkTo(methodOn(FluxoPedidoController.class)
				.cancelar(codigoPedido)).withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId, String rel) {	//aula 19.21
		return linkTo(methodOn(RestauranteController.class)
				.buscar(restauranteId)).withRel(rel);
	}

	public Link linkToRestaurante(Long restauranteId) {	//aula 19.21
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}
	/*..............................................
	public Link linkToRestaurantes(String rel) {	//aula 19.24
	String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();//aula 19.26
//		return linkTo(RestauranteController.class).withRel(rel);	//coment: aula 19.26
	    return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);//aula 19.26
	}
	public Link linkToRestaurantes() {		//aula 19.24
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}.................................*/
	public Link linkToRestaurantes(String rel) {		//aula 19.24
		String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();//aula19.26
//		return linkTo(RestauranteController.class).withRel(rel);	//coment: aula 19.26
		return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
	}
//	public Link linkToRestaurantes(String rel) {	//aula 19.24
	public Link linkToRestaurantes() {	//aula 19.24
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {	//aula 19.24
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.listar(restauranteId)).withRel(rel);
	}
	public Link linkToRestauranteFormasPagamento(Long restauranteId) {	//aula 19.27
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}
	public Link linkToRestauranteFormaPagamentoDesassociacao(	//aula 19.28
			Long restauranteId, Long formaPagamentoId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)	//aula 19.28
				.desassociar(restauranteId, formaPagamentoId)).withRel(rel);
	}
	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {	//aula 19.29
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.associar(restauranteId, null)).withRel(rel);
	}	
	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {		//aula 19.25
		return linkTo(methodOn(RestauranteController.class)
				.abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {		//aula 19.25
		return linkTo(methodOn(RestauranteController.class)
				.fechar(restauranteId)).withRel(rel);
	}
	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {		//aula 19.25
		return linkTo(methodOn(RestauranteController.class)
				.inativar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {		//aula 19.25
		return linkTo(methodOn(RestauranteController.class)
				.ativar(restauranteId)).withRel(rel);
	}

	public Link linkToUsuario(Long usuarioId, String rel) {	//aula 19.24
		return linkTo(methodOn(UsuarioController.class)
				.buscar(usuarioId)).withRel(rel);
	}
	public Link linkToUsuario(Long usuarioId) {
		return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}
	public Link linkToUsuarios(String rel) {
		return linkTo(UsuarioController.class).withRel(rel);
	}
	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}
	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {	//aula 19.35
		return linkTo(methodOn(UsuarioGrupoController.class)
				.associar(usuarioId, null)).withRel(rel);
	}
	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {	//aula 19.35
		return linkTo(methodOn(UsuarioGrupoController.class)
				.desassociar(usuarioId, grupoId)).withRel(rel);
	}
	public Link linkToGruposUsuario(Long usuarioId, String rel) {	//aula 19.21
		return linkTo(methodOn(UsuarioGrupoController.class)
				.listar(usuarioId)).withRel(rel);
	}	
	public Link linkToGruposUsuario(Long usuarioId) {		//aula 19.21
		return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}
	public Link linkToGrupos(String rel) {	//aula 19.33
		return linkTo(GrupoController.class).withRel(rel);
	}

	public Link linkToGrupos() {	//aula 19.33
		return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String rel) {	//aula 19.33
		return linkTo(methodOn(GrupoPermissaoController.class)
				.listar(grupoId)).withRel(rel);
	}
	public Link linkToPermissoes(String rel) {		//aula 19.34
		return linkTo(PermissaoController.class).withRel(rel);
	}
	public Link linkToPermissoes() {	//aula 19.34
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}
	public Link linkToGrupoPermissoes(Long grupoId) {	//aula 19.34
		return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}
	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {	//aula 19.34
		return linkTo(methodOn(GrupoPermissaoController.class)
				.associar(grupoId, null)).withRel(rel);
	}
	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {	//aula 19.34
		return linkTo(methodOn(GrupoPermissaoController.class)
				.desassociar(grupoId, permissaoId)).withRel(rel);
	}
	public Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {	//aula 19.31
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.listar(restauranteId)).withRel(rel);
	}
	public Link linkToResponsaveisRestaurante(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	public Link linkToRestauranteResponsaveis(Long restauranteId) {		//aula 19.30
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}
	public Link linkToRestauranteResponsavelDesassociacao(		//aula 19.30
			Long restauranteId, Long usuarioId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.desassociar(restauranteId, usuarioId)).withRel(rel);
	}
	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {		//aula 19.30
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.associar(restauranteId, null)).withRel(rel);
	}
	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.buscar(formaPagamentoId, null)).withRel(rel);
	}
	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}
	public Link linkToFormasPagamento(String rel) {		//aula 19.27
		return linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormasPagamento() {		//aula 19.27
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}
	public Link linkToCidade(Long cidadeId, String rel) {	//aula 19.21
		return linkTo(methodOn(CidadeController.class)
				.buscar(cidadeId)).withRel(rel);
	}
	public Link linkToCidade(Long cidadeId) {
		return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}
	public Link linkToCidades(String rel) {
		return linkTo(CidadeController.class).withRel(rel);
	}
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}
	public Link linkToEstado(Long estadoId, String rel) {	//aula 19.21
		return linkTo(methodOn(EstadoController.class)
				.buscar(estadoId)).withRel(rel);
	}
	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}
	public Link linkToEstados(String rel) {
		return linkTo(EstadoController.class).withRel(rel);
	}
	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}
	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {	//aula 19.21
		return linkTo(methodOn(RestauranteProdutoController.class)
				.buscar(restauranteId, produtoId))
				.withRel(rel);
	}
	public Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	public Link linkToProdutos(Long restauranteId, String rel) {	//aula 19.31
		return linkTo(methodOn(RestauranteProdutoController.class)
				.listar(restauranteId, null)).withRel(rel);
	}
	public Link linkToProdutos(Long restauranteId) {	//aula 19.31
		return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {	//aula 19.31
		return linkTo(methodOn(RestauranteProdutoFotoController.class)
				.buscar(restauranteId, produtoId)).withRel(rel);
	}
	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {	//aula 19.31
		return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}
	public Link linkToCozinhas(String rel) {	//aula 19.21
		return linkTo(CozinhaController.class).withRel(rel);
	}
	public Link linkToCozinhas() {		//aula 19.24
		return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	public Link linkToCozinha(Long cozinhaId, String rel) {		//aula 19.24
		return linkTo(methodOn(CozinhaController.class)
				.buscar(cozinhaId)).withRel(rel);
	}
	public Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}
	public Link linkToEstatisticas(String rel) {	//aula 19.37
		return linkTo(EstatisticasController.class).withRel(rel);
	}
	public Link linkToEstatisticasVendasDiarias(String rel) {	//aula 19.37
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
		String pedidosUrl = linkTo(methodOn(EstatisticasController.class)	
				.consultarVendasDiarias(null, null)).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), rel);
	}
}