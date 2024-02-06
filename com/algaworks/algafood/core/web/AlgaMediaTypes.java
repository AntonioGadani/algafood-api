package com.algaworks.algafood.core.web;
import org.springframework.http.MediaType;

public class AlgaMediaTypes {		//aula 20.10
	public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";
	public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);
	
	public static final String V2_APPLICATION_JSON_VALUE = "application/vnd.algafood.v2+json";			//aula 20.11
	public static final MediaType V2_APPLICATION_JSON = MediaType.valueOf(V2_APPLICATION_JSON_VALUE);	//aula 20.11
}