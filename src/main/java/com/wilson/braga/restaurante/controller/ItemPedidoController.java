package com.wilson.braga.restaurante.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wilson.braga.restaurante.dto.ItemPedidoDTO;
import com.wilson.braga.restaurante.service.ItemPedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/itensDoPedidos")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping("/{pedidoId}/itens")
	public ResponseEntity<List<ItemPedidoDTO>> findByPedidoId(@PathVariable Long pedidoId) {
		List<ItemPedidoDTO> itens = itemPedidoService.findByPedidoId(pedidoId);
		return ResponseEntity.ok(itens);
	}

	@GetMapping("/item/{id}")
	public ResponseEntity<ItemPedidoDTO> findById(@PathVariable Long id) {
		ItemPedidoDTO item = itemPedidoService.findById(id);
		return ResponseEntity.ok(item);
	}

	@PostMapping("/pedidos/{pedidoId}/itens")
	public ResponseEntity<ItemPedidoDTO> create(@PathVariable Long pedidoId,
			@Valid @RequestBody ItemPedidoDTO itemDTO) {
		ItemPedidoDTO novoItem = itemPedidoService.save(itemDTO, pedidoId);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoItem.getId())
				.toUri();
		return ResponseEntity.created(uri).body(novoItem);
	}
	
	 @PutMapping("/itens/{id}")
	public ResponseEntity<ItemPedidoDTO> update(@PathVariable Long id, @Valid @RequestBody ItemPedidoDTO itemDTO) {

		ItemPedidoDTO itemAtualizado = itemPedidoService.update(id, itemDTO);

		return ResponseEntity.ok(itemAtualizado);
	}

}
