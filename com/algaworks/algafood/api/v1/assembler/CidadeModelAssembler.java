package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;

@Component
//public class CidadeModelAssembler {		//aula 11.20, 19.11, 19.21, 19.24, 23.40
public class CidadeModelAssembler 
			extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {
			//acima: Cidade: Origem dos dados;	CidadeModel: Representação dos dados
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
		
	public CidadeModelAssembler() {	//construtor
		super(CidadeController.class, CidadeModel.class);
	}
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade); //busca cidadeId
		
		modelMapper.map(cidade, cidadeModel);	//copia dados do destino para a representação
		
		if (algaSecurity.podeConsultarCidades()) {		//aula 23.40
			cidadeModel.add(algaLinks.linkToCidades("cidades"));
		}
		
		if (algaSecurity.podeConsultarEstados()) {		//aula 23.40
			cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
		}
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarCidades()) {		//aula 23.40
			collectionModel.add(algaLinks.linkToCidades());
		}
		
		return collectionModel;
	}
	
}
		
		
/* Links para Cidades e Estado na classe AlgaLinks....................aula 19.21 e 23.40		
		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));		//busca coleção de cidades
		
		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeModel.getEstado().getId())).withSelfRel());  //busca um estado
..............................................................................................*		
	    cidadeModel.add(algaLinks.linkToCidades("cidades"));	//aula 19.21. Links Cidades, Estado
	    cidadeModel.getEstado().add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
		return cidadeModel;
	}	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
			//	.add(linkTo(CidadeController.class).withSelfRel());	//aula 19.24
			.add(algaLinks.linkToCidades());	//aula 19.24
	}	
}............................................................................................aula 23.40*/