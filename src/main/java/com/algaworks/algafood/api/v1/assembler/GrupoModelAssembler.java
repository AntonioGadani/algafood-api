package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Grupo;

@Component
//	public class GrupoModelAssembler {		//aula 12.8, 12.16, 19.33
	public class GrupoModelAssembler 
    extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {	//aula 19.33, 23.40

	@Autowired
	private ModelMapper modelMapper;		
	/*   Esta é uma classe que monta um GrupoModel(DTO) e, 
    será injetada no controlador(GrupoController   
    Método toModel foi iniciado na aula 11.10(RestauranteModel). Pega todas as propriedades 
    de Grupo e cola em GrupoModel.	*/
    @Autowired
    private AlgaLinks algaLinks;	//aula 19.33
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40
    
    public GrupoModelAssembler() {	//aula 19.33
        super(GrupoController.class, GrupoModel.class);
    }
/* 	public GrupoModel toModel(Grupo grupo) {........................................código substituído na aula 19.33
	return modelMapper.map(grupo, GrupoModel.class);
	}.............................................................................................................*/
    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoModel.add(algaLinks.linkToGrupos("grupos"));
			
			grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(algaLinks.linkToGrupos());
		}
		
		return collectionModel;
	}
}


        
 /*       grupoModel.add(algaLinks.linkToGrupos("grupos"));		trecho substituído na aula 23.40
        
        grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        
        return grupoModel;
        
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoModel.add(algaLinks.linkToGrupos("grupos"));
			
			grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(algaLinks.linkToGrupos());
		}
		
		return collectionModel;
	}
	
}
    }
    
    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGrupos());
    }            
}

 *..............................................................................código substituído na aula 19.33   
    abaixo, De uma lista de grupos, devolvemos uma lista de gruposModel
//public List<GrupoModel> toCollectionModel(List<Grupo> grupos) { > "List">"Collection, aula 12.16
	public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
		return grupos.stream()    	*stream() evita codificação de laços
									.map mapeia propriedades de Grupo para GrupoModel
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());     /*converte de uma lista lista para outra.
													lista de doamin.model para api.model 
	}....................................................................................aula 23.40..*/
	
