package com.algaworks.algafood.api.v1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
//import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;

@RestController
@RequestMapping(path = "/v1")	//antes:@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)//aula 20.14
public class RootEntryPointController {		//aula 19.36, 19.37, 20.14, 23.40
	@Autowired
	private AlgaLinks algaLinks;
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40
	
	@GetMapping
	public RootEntryPointModel root() {		//aula 23.40, até o final
		var rootEntryPointModel = new RootEntryPointModel();
		
		if (algaSecurity.podeConsultarCozinhas()) {
			rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
		}
		
		if (algaSecurity.podePesquisarPedidos()) {
			rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
			rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
			rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
		}
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
		}
		
		if (algaSecurity.podeConsultarEstados()) {
			rootEntryPointModel.add(algaLinks.linkToEstados("estados"));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
		}
		
		if (algaSecurity.podeConsultarEstatisticas()) {
			rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));
		}
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}
/*...............................................................trecho comentado na aula 23.40	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(algaLinks.linkToEstados("estados"));
		rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));	//aula 19.37
		rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
		return rootEntryPointModel;
	}
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
}..................................................................................aula 23.40      */