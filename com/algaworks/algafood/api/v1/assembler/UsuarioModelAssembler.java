package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import com.algaworks.algafood.api.v1.AlgaLinks;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler 	//   aula 12.9, 12.17, 19.12, 19.21, 19.24, 19.40
extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 19.40
	
	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}
	
	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			usuarioModel.add(algaLinks.linkToUsuarios("usuarios"));
			
			usuarioModel.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		}
		
		return usuarioModel;
	}
	
	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		CollectionModel<UsuarioModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {	//aula 23.40
			collectionModel.add(algaLinks.linkToUsuarios());
		}
		
		return collectionModel;
	}
	
}

/*   Esta é uma classe que monta um UsuarioModel(DTO) e, 
será injetada no controlador(UsuarioController. Método toModel foi iniciado na
 aula 11.10(RestauranteModel). Pega todas as propriedades de Usuario e cola em UsuarioModel.
@Component
//public class UsuarioModelAssembler {		//   aula 12.9, 12.17, 19.12, 19.21, 19.24
	
	public class UsuarioModelAssembler 
				extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	
	public UsuarioModelAssembler() {
	super(UsuarioController.class, UsuarioModel.class);//classe super pede controlador/representação
	}
	//acima: Usuario: Origem dos dados;	UsuarioModelModel: Representação dos dados
	
	//public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {  //aula 12.17
	//public List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {  coment: aula 12.17
		
	@Override		//aula 19.12
		public UsuarioModel toModel(Usuario usuario) {
			UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);//busca UsuarioI
			modelMapper.map(usuario, usuarioModel);//copia dados do destino para a representação
*	abaixo, link refatorado para classe AlgaLinks.............aula 19.21	
 			* usuarioModel.add(linkTo(UsuarioController.class).withRel("usuarios"));
			//acima: //busca coleção de usuários			
			usuarioModel.add(linkTo(methodOn(UsuarioGrupoController.class)
					.listar(usuario.getId())).withRel("grupos-usuario"));//busca um grupo de usuarios
			*...................................................................................*
			usuarioModel.add(algaLinks.linkToUsuarios("usuarios"));  //aula 19.21
			usuarioModel.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
			return usuarioModel;
		}
		
		@Override
		public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
			return super.toCollectionModel(entities)
			//	.add(linkTo(UsuarioController.class).withSelfRel());	//aula 19.24
				.add(algaLinks.linkToUsuarios());		//aula 19.24
		}		
	}																			Coment: aula 23.40  */