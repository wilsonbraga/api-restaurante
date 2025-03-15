package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	
	List<Pagamento> findByVendaId(Long vendaId); // Buscar pagamentos de uma venda específica
}
