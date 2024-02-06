package com.algaworks.algafood.domain.model;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
//import java.util.ArrayList;		aula 12.16, substituído por HashSet
//import java.util.List;			aula 12.16, substituído por Set
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data     /*Todos juntos agora: um atalho para @ToString, @EqualsAndHashCode, 
@Getter em todos os campos e @Setter em todos os campos não finais 
e @RequiredArgsConstructor!	*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {		//aula 12.9, 12.16, 23.15
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@CreationTimestamp	//indica data e hora do cadastro do usuário
	@Column(nullable = false, columnDefinition = "datetime")	//collumnDefinition / eliminar os milisegundos
	private OffsetDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	//private List<Grupo> grupos = new ArrayList<>()	//alterado para "Set" na aula 12.16
	private Set<Grupo> grupos = new HashSet<>();

		/*	public boolean senhaCoincideCom(String senha) {
				return getSenha().equals(senha);
			}	
			public boolean senhaNaoCoincideCom(String senha) {
				return !senhaCoincideCom(senha);
			}.....................................................métodos excluídos na aula 23.15  */
/*................................................aula 12.16..................................*/
	public boolean removerGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
	
	public boolean adicionarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}
	    /*............................................aula 12.16..............................*/
	public boolean isNovo() {		//aula 23.15
		return getId() == null;
	}
}

