package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

//@Repository			//aulas 12.13, 13.05, 14.6, 14.7
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {
	//ProdutoRepositoryQueries é uma interface para salvar a foto
	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
	Optional<Produto> findById(@Param("restaurante") Long restauranteId, 
			@Param("produto") Long produtoId);
	
	List<Produto> findTodosByRestaurante(Restaurante restaurante);
	
	//Método para filtra somente os produtos ativos de um restaurante
	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")		//aula 13.05
	List <Produto> findAtivosByRestaurante(Restaurante restaurante);
	//aula 14.7, abaixo: join entre produto e restaurante. foto{1} com restaurante{1}
	@Query("select f from FotoProduto f join f.produto p "		//aula 14.7
			+ "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
	
}