package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {	//aula 13.19

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}