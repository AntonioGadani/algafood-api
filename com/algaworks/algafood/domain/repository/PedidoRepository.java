package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

//import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

//@Repository			//12.19, 12.20, 12.25, 13.6, 23.31
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>,
											JpaSpecificationExecutor<Pedido>{   //aula 13.6
	Optional<Pedido> findByCodigo(String codigo);//aula 12.25:inserindo código UUid
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha" )
	List<Pedido> findAll();
	
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);	//aula 23.31
}