package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
//public class FotoProdutoModelAssembler {		//aula 14.06, 19.32
public class FotoProdutoModelAssembler 		//aula 14.06, 19.32, 23.40
    extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.32
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
	
	    public FotoProdutoModelAssembler() {	//construtor, aula 19.32
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }
/*	public FotoProdutoModel toModel(FotoProduto foto) {
		return modelMapper.map(foto, FotoProdutoModel.class);................aula 19.32  */
	    @Override
	    public FotoProdutoModel toModel(FotoProduto foto) {	//aula 19.32
	        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
	        
			// Quem pode consultar restaurantes, também pode consultar os produtos e fotos
			if (algaSecurity.podeConsultarRestaurantes()) {		//aula 23.40
				fotoProdutoModel.add(algaLinks.linkToFotoProduto(
						foto.getRestauranteId(), foto.getProduto().getId()));
				
				fotoProdutoModel.add(algaLinks.linkToProduto(
						foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
			}
			
			return fotoProdutoModel;
		}
		
	}
	       
/*.............................................................trecho substiuído na aula 23.40	        
	        fotoProdutoModel.add(algaLinks.linkToFotoProduto(
	                foto.getRestauranteId(), foto.getProduto().getId()));
	        fotoProdutoModel.add(algaLinks.linkToProduto(
	                foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
	        return fotoProdutoModel;
	    }   
	}...................................................................................aula 23.40*/