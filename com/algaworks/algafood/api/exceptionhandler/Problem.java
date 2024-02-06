package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)  //só inclua na representação propriedade que não for nula
@Getter
@Builder
public class Problem {	//8.18, 8.28, 8.29, 9,4, 9.18, 11.8
	private Integer status;
	private OffsetDateTime timestamp;
	private String type;
	private String title;
	private String detail;
	private String userMessage;
	//private List<Field> fields;
	private List<Object> objects;	//aula 9.18
	
	@Getter
	@Builder
	public static class Object {
		
		private String name;
		private String userMessage;
		
	}
	
}