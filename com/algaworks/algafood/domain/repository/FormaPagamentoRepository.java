package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FormaPagamento;

//@Repository	//12.5, 17.9, //12.5,17.9
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	//List<FormaPagamento> listar();
	@Query("select max(dataAtualizacao) from FormaPagamento")	//aula 17.9
	OffsetDateTime getDataUltimaAtualizacao();
	
	@Query("select dataAtualizacao from FormaPagamento where id = :formaPagamentoId")	//aula 17.10
	OffsetDateTime getDataAtualizacaoById(Long formaPagamentoId);
}