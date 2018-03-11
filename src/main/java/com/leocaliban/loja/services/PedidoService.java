package com.leocaliban.loja.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Boleto;
import com.leocaliban.loja.domain.ItemPedido;
import com.leocaliban.loja.domain.Pedido;
import com.leocaliban.loja.domain.enums.EstadoPagamento;
import com.leocaliban.loja.repositories.ClienteRepository;
import com.leocaliban.loja.repositories.ItemPedidoRepository;
import com.leocaliban.loja.repositories.PagamentoRepository;
import com.leocaliban.loja.repositories.PedidoRepository;
import com.leocaliban.loja.repositories.ProdutoRepository;
import com.leocaliban.loja.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class PedidoService {
	
	//Instancia automaticamente a dependencia
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Long id) {
		Pedido obj = repository.findOne(id);
		if (obj == null) {
			throw new ObjetoNaoEncontradoException("Objeto Não Encontrado! Id: "+id+", Tipo: "
					+Pedido.class.getName());
		}
		return obj;
	}
	
	public Pedido salvar(Pedido obj) {
		obj.setId(null);
		obj.setInstanteCompra(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof Boleto) {
			Boleto pagamentoBoleto = (Boleto) obj.getPagamento();
			boletoService.preenceherPagamentoBoleto(pagamentoBoleto, obj.getInstanteCompra());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido itemPedido : obj.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoRepository.findOne(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
}
