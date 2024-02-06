package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;

@Component
//	public class ProdutoModelAssembler {	//aulas 12.13, 19.31, 
	public class ProdutoModelAssembler 
			extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {	/*aula 19.31, 19.32
																					23.40  */
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.31
	
	@Autowired
	private AlgaSecurity algaSecurity;	//aula 23.40
	
	public ProdutoModelAssembler() {	//construtor, aula 19.31
	        super(RestauranteProdutoController.class, ProdutoModel.class);
		    }
	
    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
        		produto.getId(), produto, produto.getRestaurante().getId());
         
        modelMapper.map(produto, produtoModel);
        
		// Quem pode consultar restaurantes, também pode consultar os produtos e fotos
		if (algaSecurity.podeConsultarRestaurantes()) {	//aula 23.40
			produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

			produtoModel.add(algaLinks.linkToFotoProduto(
					produto.getRestaurante().getId(), produto.getId(), "foto"));
		}
		
		return produtoModel;
	}
	
}
        
/*          produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
          produtoModel.add(algaLinks.linkToFotoProduto(
                  produto.getRestaurante().getId(), produto.getId(), "foto"));	//aula 19.32
          return produtoModel;
    }   ..................................................................coment: aula 23.40   */
