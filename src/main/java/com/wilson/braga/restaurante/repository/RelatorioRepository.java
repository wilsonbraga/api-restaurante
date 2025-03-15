package com.wilson.braga.restaurante.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.Relatorio;
import com.wilson.braga.restaurante.model.TipoRelatorio;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
	
	// Buscar relatórios por tipo e período
	List<Relatorio> findByTipoAndDataInicialBetween(TipoRelatorio tipo, LocalDate dataInicial, LocalDate dataFim);
}
