package com.wilson.braga.restaurante.dto;

import com.wilson.braga.restaurante.model.StatusMesa;

public class MesaDTO {
	
	private Long id;
    private String numero;
    private int capacidade;
    private StatusMesa status;
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public StatusMesa getStatus() {
		return status;
	}
	public void setStatus(StatusMesa status) {
		this.status = status;
	}
    
    
}
