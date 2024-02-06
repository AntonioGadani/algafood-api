package com.algaworks.algafood.domain.model;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;
//@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", //anotação personalizada
//descricaoObrigatoria = "Frete Grátis")	//aula 9.17 e 9.18, para fins didádicos
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {	//3.4, 3.6, 3.16, 3.17, 6.1, 6,2, 6,4, 6,5, 6.8, 9.5, 9.2, 9.6, 
							// 9.7, 9.8, 9.17, 11.11, 11.21, 12.4, 12.12, 12.14, 12.17, 19.25
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
/*	@NotNull
	@NotEmpty
	@NotBlank		//(message = "nome é obrigatório")   coment: aula 12.4. anotações nos DTO     */
	@Column(nullable = false)
	private String nome;
	
	//@NotNull  //excluído aula 11.11, @NotNUll, @PositiveOeZero, @Multilplo(anotação personalisada.
	//@PositiveOrZero//(message = "{TaxaFrete.invalida}"), para uso do resource bundle do BeanValidation
	//@Multiplo(numero = 5)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//@Valid	//valide a propriedade cozinha  //excluído aula 11.11
	//@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)	//aula 9.8 excluído aula 11.11
	//@NotNull   //excluído aula 11.11
	@ManyToOne
	@JoinColumn(name="cozinha_id", nullable = false)
	private Cozinha cozinha;

	@Embedded	//Restaurante embute dados da classe Endereco
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	
	@CreationTimestamp	//indica data e hora da primeira vez que um restaurante é cadastrado
	@Column(nullable = false, columnDefinition = "datetime")	//collumnDefinition / eliminar os milisegundos
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp	//indica data e hora sempre que um restaurante for atualizado
	@Column(nullable = false, columnDefinition = "datetime")//collumnDefinition / eliminar os milisegundos
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	//private List<FormaPagamento> formasPagamento = new ArrayList<>();  //coment:aula 12.12
	private Set<FormaPagamento> formasPagamento = new HashSet<>();  //aula 12.12
	/* acima "Set" é um conjunto e não aceita elementos duplicados */
/*............................aula 12.17................................................*/
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	/*...............................................................aula 12.17............*/
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	public void ativar() {		//aula 12.4
		setAtivo(true);
	}
	
	public void inativar() {		//aula 12.4
		setAtivo(false);
	}
	/*................................................aula 12.14........................*/
	public void abrir() {
	    setAberto(true);
	}

	public void fechar() {
	    setAberto(false);
	}
	/*acima............................................aula 12.14........................*/
	/*abaixo............................................aula 19.25........................*/
	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}

	public boolean isInativo() {
		return !isAtivo();
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public boolean aberturaPermitida() {
		return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
		return isInativo();
	}

	public boolean inativacaoPermitida() {
		return isAtivo();
	}

	public boolean fechamentoPermitido() {
		return isAberto();
	}
	/*acima............................................aula 19.25........................*/
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {	//aula 12.12
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {  //aula 12.12
		return getFormasPagamento().add(formaPagamento);
	}
/*..........................aula 12.21....................................................*/
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}
	
	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
		return !aceitaFormaPagamento(formaPagamento);
	}
	/*..........................Fim aula 12.21.............................................*/
/*.........................................................aula 12.17......................*/
	public boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}
	
	public boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}
/*.........................................................aula 12.17......................*/	
}
