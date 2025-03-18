package com.wilson.braga.restaurante.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	private String descricao;

	@Column(nullable = false)
	private double preco;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriaProduto categoria;
	
	private String imagem;
	
	private Boolean disponivel;
	
	private int tempoPreparoMedio;
	
	private int totalVendas; // para estatísticas

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public CategoriaProduto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public Boolean getDisponivel() {
		return disponivel;
	}
	
	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}
	public int getTempoPreparoMedio() {
		return tempoPreparoMedio;
	}
	
	public void setTempoPreparoMedio(int tempoPreparoMedio) {
		this.tempoPreparoMedio = tempoPreparoMedio;
	}
	
	public int getTotalVendas() {
		return totalVendas;
	}
	
	public void setTotalVendas(int totalVendas) {
		this.totalVendas = totalVendas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

}
