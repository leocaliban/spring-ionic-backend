package com.leocaliban.loja.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.domain.Boleto;
import com.leocaliban.loja.domain.ItemPedido;
import com.leocaliban.loja.domain.Pedido;
import com.leocaliban.loja.domain.enums.EstadoPagamento;
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
			itemPedido.setPreco(produtoRepository.findOne(itemPedido.getProduto().getId()).getPreco());
			itemPedido.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
	}
}
