package com.algaworks.algafood.core.storage;
import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Component	 //é um componente Spring
@ConfigurationProperties("algafood.storage") 	//9

	public class StorageProperties { 	//aula 14.20, 14.26
		private Local local = new Local();
		private S3 s3 = new S3();
		private TipoStorage tipo = TipoStorage.LOCAL;  //por padrão o tipo é local
		public enum TipoStorage {
				LOCAL, S3
		}
@Getter
@Setter
	public class Local {
		private Path diretorioFotos;
	}
@Getter
@Setter
public class S3 {
private String idChaveAcesso;
private String chaveAcessoSecreta;
private String bucket;
//private String regiao;	//coment: aula 14.22
private Regions regiao; 	//aula 14.22
private String diretorioFotos;
}
}
