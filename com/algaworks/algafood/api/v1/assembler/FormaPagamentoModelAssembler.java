package com.algaworks.algafood.api.v1.assembler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component		//classe é um componente do Spring
//public class FormaPagamentoModelAssembler {	//aula 12.05.  Ver aulas:11.10, 11.12, 19.27, 23.40
public class FormaPagamentoModelAssembler 
	extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {	//aula 19.27	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlgaLinks algaLinks;	//aula 19.27
	@Autowired
	private AlgaSecurity algaSecurity;		//aula 23.40
	/*   Esta é uma classe que monta um FormaPagamentoModel(DTO) e, 
	     será injetada no controlador(FormaPagamentoController   
	     Método toModel foi iniciado na aula 11.10. Pega todas as propriedades de 
	     FormaPagamento e cola em FormaPagamentoModel.	*/
	public FormaPagamentoModelAssembler() {	//construtor, aula 19.27
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {	//aula 19.27
		FormaPagamentoModel formaPagamentoModel =
				createModelWithId(formaPagamento.getId(), formaPagamento);

		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {		//aula 23.40
			formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}
		
		return formaPagamentoModel;
	}
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {		//aula 23.40
			collectionModel.add(algaLinks.linkToFormasPagamento());
		}
			
		return collectionModel;
	}
	
}

/*................................................................trecho comentado na aula 23.40
		formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));

		return formaPagamentoModel;
	}

	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities)		//aula 19.27
				.add(algaLinks.linkToFormasPagamento());
	}

}
/*...............................................código substituído na aula 19.27.............
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	*abaixo, De uma lista de formasPagamento, devolvemos uma lista de formaPagamentoModel
	public List<FormaPagamentoModel> toCollectionModel(List<FormaPagamento> formasPagamentos) {
	...........................Substituímos List por Collection, na aula 12.12  ..........*
		public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
		return formasPagamentos.stream()	/*stream() evita codificação de laços
				.map mapeia propriedades de FormaPagamento para FormaPagamentoModel   *
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList()); /*converte de uma lista lista para outra.
												lista de doamain.model para api.model *
	}................................................................fim, aula 19.27, e 23.40........*/
	
