package com.algaworks.algafood.domain.model;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;
import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data     /*Todos juntos agora: um atalho para @ToString, @EqualsAndHashCode, 
@Getter em todos os campos e @Setter em todos os campos não finais 
e @RequiredArgsConstructor!	*/
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
//public class Pedido {    alteração na aula 15.11
public class Pedido extends AbstractAggregateRoot<Pedido> {	/*aula 7.12, 11.8, 12.19, 12.21, 12.24
																   15.11, 19.23.....*/ 
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	//@Column(nullable = false)
	private BigDecimal subtotal;
	
	//@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	//@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@Embedded	//Pedido embute dados da classe Endereco
	private Endereco enderecoEntrega;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;	//aula 12.19, 12.22, 15.14
	
	@CreationTimestamp	//indica data e hora da primeira vez que um restaurante é cadastrado
	private OffsetDateTime dataCriacao;
	//@Column(nullable = false, columnDefinition = "datetime")//collumnDefinition / eliminar os milisegundos

	//@CreationTimestamp	//indica data e hora da primeira vez que um restaurante é cadastrado
	private OffsetDateTime dataConfirmacao;
	//@Column(columnDefinition = "datetime")	//collumnDefinition / eliminar os milisegundos

	
	//@CreationTimestamp	//indica data e hora da primeira vez que um restaurante é cadastrado
	private OffsetDateTime dataCancelamento;
	//@Column(columnDefinition = "datetime")	//collumnDefinition / eliminar os milisegundos

	
	//@CreationTimestamp	//indica data e hora da primeira vez que um restaurante é cadastrado
	//@Column(columnDefinition = "datetime")	//collumnDefinition / eliminar os milisegundos
	private OffsetDateTime dataEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	//@OneToMany(mappedBy = "pedido") //em ItemPedido tem um atributo "Pedido"
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)	//aula 12.21
	private List<ItemPedido> itens = new ArrayList<>();
/*.................................................classe 12.19.............................*/	
	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calcularPrecoTotal); //aula 12.21
		this.subtotal = getItens().stream()
		.map(item -> item.getPrecoTotal())
		.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.valorTotal = this.subtotal.add(this.taxaFrete);
		}
		public void definirFrete() {
		setTaxaFrete(getRestaurante().getTaxaFrete());
		}

	
	public void atribuirPedidoAosItens() {
		getItens().forEach(item -> item.setPedido(this));
	}
	/*...................................................fim classe 12.19.......................*/
	/*...........................aula 12.24.....................................................*/
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		registerEvent(new PedidoConfirmadoEvent(this));		//aula 15.11 (eventos DDD)
	}
	
	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		  registerEvent(new PedidoCanceladoEvent(this));	//aula 15.14
	}
	public boolean podeSerConfirmado() {	//aula 19.23
		return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
	}

	public boolean podeSerEntregue() {	//aula 19.23
		return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
	}

	public boolean podeSerCancelado() {	//aula 19.23
		return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(
			String.format("Status do pedido %d não pode ser alterado de %s para %s",
			getCodigo(), getStatus().getDescricao(), //getId(), getStatus().getDescricao/Aula 12.25 
							novoStatus.getDescricao()));
		}
		
		this.status = novoStatus;
	}
	/*...........................Fim     aula 12.24..............................................*/
	@PrePersist
	private void gerarCodigo() {	//aula 12.25
		setCodigo(UUID.randomUUID().toString());
	}
}

