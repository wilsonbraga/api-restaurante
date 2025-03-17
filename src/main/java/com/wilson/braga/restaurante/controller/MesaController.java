package com.wilson.braga.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wilson.braga.restaurante.dto.MesaDTO;
import com.wilson.braga.restaurante.service.MesaService;

@RestController
@RequestMapping("/mesas")
public class MesaController {

	@Autowired
	private MesaService mesaService;

	// Endpoint para buscar mesas disponíveis
	// http://localhost:8080/restaurante/disponiveis
	@GetMapping("/disponiveis")
	public ResponseEntity<List<MesaDTO>> buscarMesasDisponiveis() {
		List<MesaDTO> mesas = mesaService.buscarMesasDisponiveis();
		return ResponseEntity.ok(mesas);
	}

	// Endpoint para buscar mesas por capacidade mínim
	// http://localhost:8080/restaurante/mesas/capacidade?capacidade=4
	@GetMapping("/capacidade")
	public ResponseEntity<List<MesaDTO>> buscarMesasPorCapacidadeMinima(@RequestParam int capacidade) {
		List<MesaDTO> mesas = mesaService.buscarMesasPorCapacidadeMinima(capacidade);
		return ResponseEntity.ok(mesas);
	}

	// Endpoint para buscar mesas ocupadas por um garçom específico e status
	@GetMapping("/ocupadas-por-garcom/{garcomId}")
	public ResponseEntity<List<MesaDTO>> buscarMesasPorGarcomEStatus(@PathVariable Long garcomId) {
		List<MesaDTO> mesas = mesaService.buscarMesasOcupadasPorGarcom(garcomId);
		return ResponseEntity.ok(mesas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MesaDTO> buscarMesaPorId(@PathVariable Long id) {
		return mesaService.buscarMesaPorId(id).map(ResponseEntity::ok) // Retorna 200 OK com a Mesa
				.orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 Not Found se o Mesa não existir
	}

}
