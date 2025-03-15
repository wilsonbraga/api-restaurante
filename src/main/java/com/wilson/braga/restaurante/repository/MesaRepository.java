package com.wilson.braga.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.model.Mesa;
import com.wilson.braga.restaurante.model.StatusMesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
	
	List<Mesa> findByStatus(StatusMesa status); //Buscar mesas por status
}
