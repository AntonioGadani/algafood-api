package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler 	//aula 11.20, 19.14	, 19.21, 19.24, 23.40
	extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula19.21
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
	
	public EstadoModelAssembler() {		//construtor, aula 19.14
		super(EstadoController.class, EstadoModel.class);
	}
	@Override
	public EstadoModel toModel(Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
		modelMapper.map(estado, estadoModel);
		
		if (algaSecurity.podeConsultarEstados()) {
			estadoModel.add(algaLinks.linkToEstados("estados"));
		}
		
		return estadoModel;
	}
	
	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarEstados()) {		//aula 23.40
			collectionModel.add(algaLinks.linkToEstados());
		}
		
		return collectionModel;
	}
	
}
		
/*.............................................................trecho comentado na aula 23.40
//		estadoModel.add(linkTo(EstadoController.class).withRel("estados"));  //aula 19.21
		estadoModel.add(algaLinks.linkToEstados("estados"));//Link p/Estados na classe AlgaLinks
		return estadoModel;
	}	
	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		return super.toCollectionModel(entities)
			//.add(linkTo(EstadoController.class).withSelfRel());   //aula 19.24
				.add(algaLinks.linkToEstados());   //aula 19.24
	}
}.........................................................................................aula 23.40*/