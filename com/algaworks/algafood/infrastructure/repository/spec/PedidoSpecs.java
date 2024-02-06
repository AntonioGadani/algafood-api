package com.algaworks.algafood.infrastructure.repository.spec;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;

public class PedidoSpecs {		//aula 13.6, 13.9, 28.4

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {		
	/*......................................................................................................
	 * 			return (root, query, builder) -> {										Coment:aula 13.9
				root.fetch("restaurante").fetch("cozinha");  //resolvendo problema do n+1
				root.fetch("cliente");						//resolvendo problema do n+1
...........................................................................................................*/
	return (root, query, builder) -> {						//aula 13.9
		if (Pedido.class.equals(query.getResultType())) {
			root.fetch("restaurante").fetch("cozinha");		//resolvendo problema do n+1
			root.fetch("cliente");							//resolvendo problema do n+1
	    }
			
			var predicates = new ArrayList<Predicate>();
			
			if (filtro.getClienteId() != null) {	//se clienteId for diferente(!=) de nulo
				predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
						//greaterThanOrEqualTo: maior ou igual dtaCriacao
						filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
						//lessThanOrEqualTo: menor ou igual dtaCriacao
						filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}