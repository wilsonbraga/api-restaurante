package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilson.braga.restaurante.dto.MesaDTO;
import com.wilson.braga.restaurante.model.Mesa;
import com.wilson.braga.restaurante.model.StatusMesa;
import com.wilson.braga.restaurante.repository.MesaRepository;

@Service
public class MesaService {
	
	@Autowired
    private MesaRepository mesaRepository;
	
	
	// Buscar mesas disponíveis
	public List<MesaDTO> buscarMesasDisponiveis(){
		List<Mesa> mesas = mesaRepository.findByStatus(StatusMesa.DISPONIVEL);
		return mesas.stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Método auxiliar para converter Mesa em MesaDTO
    private MesaDTO toDTO(Mesa mesa) {
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNumero(mesa.getNumero());
        dto.setCapacidade(mesa.getCapacidade());
        dto.setStatus(mesa.getStatus());
        return dto;
    }
	
}
