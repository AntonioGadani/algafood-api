package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Permissao;

	@Component	//classe é um componente do Spring
//	public class PermissaoModelAssembler {	//19.34
	public class PermissaoModelAssembler	//aula 12.05, 12.14, 19.34;  Ver aulas:11.10, 11.12, 23.40
		implements RepresentationModelAssembler<Permissao, PermissaoModel> {
	@Autowired
	private ModelMapper modelMapper;
	/*   Esta é uma classe que monta uma PermissaoModel(DTO) e, 
    será injetada no controlador(PermissaoController   
    Método toModel foi iniciado na aula 11.10. Pega todas as propriedades de 
    Permissao e cola em PermissaoModel.	*/
    @Autowired
    private AlgaLinks algaLinks;	//aula 19.34
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }
    
    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
    	
		CollectionModel<PermissaoModel> collectionModel 
		= RepresentationModelAssembler.super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {		//aula 23.40
		collectionModel.add(algaLinks.linkToPermissoes());
		}
	
		return collectionModel;
    	}
}

 
/*    	return RepresentationModelAssembler.super.toCollectionModel(entities).........aula 23.40
                .add(algaLinks.linkToPermissoes());
    }   
}.................................................................................aula 23.40.*/
	/*.................................................................................Trecho alterado na aula 19.34
		public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	/...........abaixo, De uma lista de permissao, devolvemos uma lista de permissaoModel
	public List<PermissaoModel> toCollectionModel(List<Permissao> permissoes) { .........*
	public List<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
		return permissoes.stream()	*stream() evita codificação de laços
									.map mapeia propriedades de Permissao para PermissaoModel   *
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}	}........................................................................................................*/