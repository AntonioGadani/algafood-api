package com.algaworks.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent	//é um componente do Spring. 
//Abaixo, extend a classe JsonSerializer e defino um serializador tipo Page, p/ qualquer tipo de página<?>
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, 		//aula 13.10
			SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();		//inicie um objeto
		// e, dentro desse objeto, informe:		
		gen.writeObjectField("content", page.getContent());	//conteúdo(elementos ou, no caso, pedidos)
		gen.writeNumberField("size", page.getSize());		//Elementos por página
		gen.writeNumberField("totalElements", page.getTotalElements());	//total de elementos
		gen.writeNumberField("totalPages", page.getTotalPages());		//total de páginas necessárias
		gen.writeNumberField("number", page.getNumber());				//Número atual da página
		
		gen.writeEndObject();	//finalize um objeto
	}

}