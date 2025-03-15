package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.CategoriaProduto;
import com.wilson.braga.restaurante.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByCategoria(CategoriaProduto categoria); // Buscar produtos por categoria

}
