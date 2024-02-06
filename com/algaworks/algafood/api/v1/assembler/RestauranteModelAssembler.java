package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 	//aula 11.12, 11.14, 19.24, 19.31, 23.40
			extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {		
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;		//aula 19.24
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		if (algaSecurity.podeConsultarRestaurantes()) {			//aula 23.40
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(
						algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}
	
			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(
						algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(
						algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}
	
			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(
						algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(
					algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			if (restauranteModel.getEndereco() != null 
					&& restauranteModel.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade().add(
						algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
					"formas-pagamento"));
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
					"responsaveis"));
		}
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(algaLinks.linkToRestaurantes());
		}
		
		return collectionModel;
	}
	
}
		
		
/*..................................................................trecho comentado na aula 23.40
 * 		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		//............................permissões, aula 19.25
		if (restaurante.ativacaoPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}
		if (restaurante.inativacaoPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}
		if (restaurante.aberturaPermitida()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}
		if (restaurante.fechamentoPermitido()) {
			restauranteModel.add(
					algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}
		restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));	//aula 19.31
		//............................permissões, aula 19.25
		restauranteModel.getCozinha().add(
				algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

		if (restauranteModel.getEndereco() != null
				&& restauranteModel.getEndereco().getCidade() != null) {	//aula 19.31
			restauranteModel.getEndereco().getCidade().add(
					algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		}
		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
				"formas-pagamento"));
		restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(),
				"responsaveis"));
		return restauranteModel;
	}

	*	CozinhaModel cozinhaModel = new CozinhaModel();
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());
		RestauranteModel restauranteModel = new RestauranteModel();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModel.setCozinha(cozinhaModel);
		return restauranteModel;					 código substituído na aula 11.14(modelmapper)
		return modelMapper.map(restaurante, RestauranteModel.class); 
	}
		public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}		*acima: (origem: Restaurante; destino: RestauranteModel *
		@Override
		public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? 	//aula 19.24
				extends Restaurante> entities) {
			return super.toCollectionModel(entities)
					.add(algaLinks.linkToRestaurantes());

		}
}...............................................................................coment: aula 23.40 */