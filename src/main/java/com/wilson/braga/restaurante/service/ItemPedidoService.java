package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilson.braga.restaurante.dto.ItemPedidoDTO;
import com.wilson.braga.restaurante.model.ItemPedido;
import com.wilson.braga.restaurante.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public List<ItemPedidoDTO> findByPedidoId(Long pedidoId){
		List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(pedidoId);
		return itens.stream().map(this::convertToDTO).collect(Collectors.toList());
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
