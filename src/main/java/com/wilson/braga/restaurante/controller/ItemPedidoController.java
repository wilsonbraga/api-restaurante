package com.wilson.braga.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilson.braga.restaurante.dto.ItemPedidoDTO;
import com.wilson.braga.restaurante.service.ItemPedidoService;

@RestController
@RequestMapping("/itensDoPedidos")
public class ItemPedidoController {
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@GetMapping("/{pedidoId}/itens")
	public ResponseEntity<List<ItemPedidoDTO>> findByPedidoId(@PathVariable Long pedidoId){
		List<ItemPedidoDTO> itens = itemPedidoService.findByPedidoId(pedidoId);
		return ResponseEntity.ok(itens);
	}
	
}
