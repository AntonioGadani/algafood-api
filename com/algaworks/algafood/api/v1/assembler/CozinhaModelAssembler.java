package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
@Component
//public class CozinhaModelAssembler {		//aula 19.15  
public class CozinhaModelAssembler		//aula 11.20, 19.15, 19.21, 23.40
		extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {  

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.21
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		
		if (algaSecurity.podeConsultarCozinhas()) {		//aula 23.40
			cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
		}
		
		return cozinhaModel;
	}
	
}

/*		cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));  //aula 19.21 e 23.40
	    cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
		return cozinhaModel;
	}
}........................................................................................aula 23.40*/
	
