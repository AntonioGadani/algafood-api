package com.algaworks.algafood.domain.listener;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {	//aulas 15.12, 15.13

	@Autowired
	private EnvioEmailService envioEmail;
	
	//@EventListener	//aula 15.13	//envia email,depois reolve o banco
	@TransactionalEventListener		//aula 15.13	// resolve o banco, depois envia o email
	//@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) //resolve o banco, depois envia email
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("emails/pedido-confirmado.html")	//aula 23.43
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();

		envioEmail.enviar(mensagem);
	}
	
}