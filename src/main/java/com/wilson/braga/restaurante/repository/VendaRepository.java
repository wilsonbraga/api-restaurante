package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.StatusVenda;
import com.wilson.braga.restaurante.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
	
	List<Venda> findByStatus(StatusVenda status); // Buscar vendas por status
}
