package com.algaworks.algafood.api.v2;

import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AlgaLinksV2 {		//aula 20.10

	public Link linkToCidades(String rel) {
		return linkTo(CidadeControllerV2.class).withRel(rel);
	}
	
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}
	public Link linkToCozinhas(String rel) {		//aula 20.15
	    return linkTo(CozinhaControllerV2.class).withRel(rel);
	}

	public Link linkToCozinhas() {		//aula 20.15
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}
	
}