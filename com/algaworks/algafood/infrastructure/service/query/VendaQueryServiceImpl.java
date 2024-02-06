//............................................................classe reescrita na aula 28.4, spring boot 3
package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		var predicates = new ArrayList<Predicate>();
		
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
		}
	      
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoFim()));
		}
	      
		predicates.add(root.get("status").in(
				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}

/*............................................................classe reescrita na aula 28.4, spring boot 3
package com.algaworks.algafood.infrastructure.service.query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;	//aula 13.15
import org.springframework.stereotype.Repository;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository				//é um repositório(componente Spring)
public class VendaQueryServiceImpl implements VendaQueryService {	//aula 13.13(Estatísitica), 13.14, 13.15, 13.16

	@PersistenceContext
	private EntityManager manager;  //injetando EntityManager do JPA para conseguir um getCriteriaBuilder()
	
	@Override	//aula 13.14: usando criteria API para obter uma lista de vendas diárias
	//public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {    coment: aula 13.16......//
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {	//aula 13.16
	var builder = manager.getCriteriaBuilder();	//builder torna-se um construtor de criteria
		var query = builder.createQuery(VendaDiaria.class);	//query: um construtor de consultas criteria
		var root = query.from(Pedido.class);	//consulta a entidade "pedido". root passa a ser o pedido
		//abaixo, var "predicates" é um array de predicados.Ver matéria: Predicate
		var predicates = new ArrayList<Predicate>();	//aula 13.15 
		
		var functionConvertTzDataCriacao = builder.function(		//aula 13.16
				"convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));	//aula 13.16
		
*coment:aula 13.16.............................................................................................
		var functionDateDataCriacao = builder.function(//captura data da criação usando 3 parâmetros(argumentos) 
				"date", 		//1º:função "date", do banco de dados;
				Date.class, 	//2º: tipo	Date.class, declarado em VendaDiaria.java: private Date data;
				root.get("dataCriacao"));	//3º: argumento "dataCriacao
....................................................................................coment aula 13.16....*
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class,//selection: um construtor de "vendaDiaria", com 3 seleções
				functionDateDataCriacao,	//pega a data de criação dos pedidos usando função, declarada acima
				builder.count(root.get("id")),	//conta os pedidos(root) pela propriedade "id"
				builder.sum(root.get("valorTotal")));	//soma as propriedades "valorTotal" de todos os pedidos(root.get)
*.......................................................................abaixo:aula 13.15.......................*		
		if (filtro.getRestauranteId() != null) {	
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			*Acima, se RestauranteId diferente de null(!=)adiciona o  predicado: getRestauranteId     
		}
	      
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoInicio()));
			*Acima, se DataCriacaoInicio diferente de null(!=)adiciona o  predicado: getDataCriacaoInicio    *
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoFim()));
			*Acima, se DataCriacaoFim diferente de null(!=)adiciona o  predicado: getDataCriacaoFim    *
		}
	      
		predicates.add(root.get("status").in(
				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		*Acima, se StaatusPedido=CONFIRMADO ou ENTREGEU)adiciona o  predicado: get("status"    *
/*...............................................................trecho acima incluso:aula 13.15.............*
		query.select(selection);	//seleção a usar: "selection"(var)
		query.where(predicates.toArray(new Predicate[0]));	//aula 13.15(desafio).Consulta com os predicados(predicates)
		query.groupBy(functionDateDataCriacao);  //agrupar usando a função "date", que elimina os minutos e segundos
		
		return manager.createQuery(query).getResultList(); //getResultList() retorna uma lista de vendas diárias
	}

}
.....................................................classe reescrita na aula 28.4, spring boot 3.....*/