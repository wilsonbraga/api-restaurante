package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.wilson.braga.restaurante.dto.MesaDTO;
import com.wilson.braga.restaurante.model.Mesa;
import com.wilson.braga.restaurante.model.StatusMesa;
import com.wilson.braga.restaurante.repository.MesaRepository;
import com.wilson.braga.restaurante.repository.UsuarioRepository;

@Service
public class MesaService {

	@Autowired
	private MesaRepository mesaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Buscar mesas disponíveis
	public List<MesaDTO> buscarMesasDisponiveis() {
		List<Mesa> mesas = mesaRepository.findByStatus(StatusMesa.DISPONIVEL);
		return mesas.stream().map(this::toDTO).collect(Collectors.toList());
	}

	// Buscar mesas por capacidade mínima
	public List<MesaDTO> buscarMesasPorCapacidadeMinima(int capacidade) {

		List<Mesa> mesas = mesaRepository.findByCapacidadeGreaterThanEqual(capacidade);
		return mesas.stream().map(this::toDTO).collect(Collectors.toList());
	}

	// Buscar mesas ocupadas por um garçom específico e status
	public List<MesaDTO> buscarMesasOcupadasPorGarcom(Long garcomId) {
		// Verifica se o garçom existe
		if (!usuarioRepository.existsById(garcomId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garçom não encontrado.");
		}
		List<Mesa> mesas = mesaRepository.findByGarcomIdAndStatusMesaOcupada(garcomId);
		return mesas.stream().map(this::toDTO).collect(Collectors.toList());
	}

	// BUSCAR MESA POR ID
	public Optional<MesaDTO> buscarMesaPorId(Long id) {
		return mesaRepository.findById(id).map(this::toDTO);
	}


	// Método auxiliar para converter Mesa em MesaDTO
	private MesaDTO toDTO(Mesa mesa) {
		MesaDTO dto = new MesaDTO();
		dto.setId(mesa.getId());
		dto.setNumero(mesa.getNumero());
		dto.setCapacidade(mesa.getCapacidade());
		dto.setStatus(mesa.getStatus());
		//TODO: FAZER O RETORNO DO PEDIDO RESPOSAVEL PELA MESA
		return dto;
	}

}
