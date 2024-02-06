package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {		//aula 14.6

	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);	//aula 14.7, delete(CatalogoProdutoFotoService)
	
}