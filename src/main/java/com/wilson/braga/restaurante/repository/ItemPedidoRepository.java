package com.wilson.braga.restaurante.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
	
	List<ItemPedido> findByPedidoId(Long pedidoId); //  Buscar itens de um pedido específico
	
	// Buscar todos os itens que contêm um produto específico
	List<ItemPedido> findByProdutoId(Long produtoId);
	
	// Buscar itens por pedido com paginação
	Page<ItemPedido> findByPedidoId(Long pedidoId, Pageable pageable);
	
	// Contar quantos pedidos contêm um determinado produto
	Long countByProdutoId(Long produtoId);
	
	// Buscar os itens mais pedidos (para estatísticas)
	@Query("SELECT i.produto.id, i.produto.nome, SUM(i.quantidade) as total "
			+ "FROM ItemPedido i "
			+ "GROUP BY i.produto.id, i.produyo.nome "
			+ "ORDER BY total DESC")
	List<ItemPedido> findMostOrderedItems(Pageable pageable);
	
	// Buscar itens de pedidos em um intervalo de datas
	@Query("SELECT i FROM ItemPedido i WHERE i.pedido.dataCriacao BETWEEN :dataInicio AND :dataFim")
	List<ItemPedido> findByPedidoDataCriacaoBetween(
			@Param("dataInicio") Date dataInicio, 
			@Param("dataFim") Date dataFim); 
	
	
}
