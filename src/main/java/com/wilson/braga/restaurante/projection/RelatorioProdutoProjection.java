package com.wilson.braga.restaurante.projection;

public interface RelatorioProdutoProjection {
	
	Long getProdutoId();

	String getNomeProduto();

	Integer getQuantidadeTotal();

	Double getValorTotal();
}
