package com.algaworks.algafood.domain.service;

import java.util.List;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {		//aula 13.13  -  Estatística, 13.16

	//List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);		coment: aula 13.16
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);	
}