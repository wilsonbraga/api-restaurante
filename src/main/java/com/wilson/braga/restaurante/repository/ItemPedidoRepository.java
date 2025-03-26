package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	List<ItemPedido> findByPedidoId(Long pedidoId); //  Buscar itens de um pedido específico
	
	// Buscar todos os itens que contêm um produto específico
	List<ItemPedido> findByProdutoId(Long produtoId);
	
	
}
