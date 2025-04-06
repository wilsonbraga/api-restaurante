package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wilson.braga.restaurante.dto.ItemPedidoDTO;
import com.wilson.braga.restaurante.model.ItemPedido;
import com.wilson.braga.restaurante.model.Pedido;
import com.wilson.braga.restaurante.model.Produto;
import com.wilson.braga.restaurante.repository.ItemPedidoRepository;
import com.wilson.braga.restaurante.repository.PedidoRepository;
import com.wilson.braga.restaurante.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;

	
	@Transactional(readOnly = true)
	public List<ItemPedidoDTO> findByPedidoId(Long pedidoId) {
		List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(pedidoId);
		return itens.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ItemPedidoDTO findById(Long id) {
		ItemPedido item = itemPedidoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado com ID: " + id));
		return convertToDTO(item);
	}
	
	@Transactional
	public ItemPedidoDTO save(ItemPedidoDTO itemDTO, Long pedidoId) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado com ID: " + pedidoId));
		
		Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + itemDTO.getProdutoId()));
		
		//VERIFICA SE O PRODUTO ESTÁ DISPONÍVEL
		if(!produto.getDisponivel()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "O Produto " + produto.getNome() + " não está disponivel.");
		}
		
		 ItemPedido item = new ItemPedido();
		 item.setPedido(pedido);
		 item.setProduto(produto); // ISSO TAMBÉM DEFINE O PREÇO UNITÁRIO
		 item.setQuantidade(itemDTO.getQuantidade());
		 
		 ItemPedido savaItem = itemPedidoRepository.save(item);
		 
		 // ATUALIZAR ESTATÍSTICAS DE VENDAS
		 produtoService.incrementarVendas(produto.getId(), item.getQuantidade());
		 
		 return convertToDTO(savaItem);
	}
	

	// Conversão entre DTO e Entidade
	private ItemPedidoDTO convertToDTO(ItemPedido item) {
		ItemPedidoDTO dto = new ItemPedidoDTO();
		dto.setId(item.getId());
		dto.setPedidoId(item.getPedido().getId());
		dto.setProdutoId(item.getProduto().getId());
		dto.setNomeProduto(item.getProduto().getNome());
		dto.setQuantidade(item.getQuantidade());
		dto.setPrecoUnitario(item.getPrecoUnitario());
		dto.setSubtotal(item.getSubtotatal());
		return dto;
	}

}
