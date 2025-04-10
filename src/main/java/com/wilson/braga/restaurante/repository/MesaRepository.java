package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.Mesa;
import com.wilson.braga.restaurante.model.StatusMesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
	
	List<Mesa> findByStatus(StatusMesa status); //Buscar mesas por status
	
	//Buscar mesas por capacidade mínima:
	List<Mesa> findByCapacidadeGreaterThanEqual(int capacidade);
	
	//Buscar mesas ocupadas por um garçom específico:
	  @Query("SELECT DISTINCT m FROM Mesa m " +
	           "JOIN m.pedidos p " +
	           "WHERE p.garcom.id = :garcomId AND m.status = 'OCUPADA'")
	List<Mesa> findByGarcomIdAndStatusMesaOcupada(@Param("garcomId") Long garcomId);
}
