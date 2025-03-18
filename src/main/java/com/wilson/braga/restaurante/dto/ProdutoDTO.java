package com.wilson.braga.restaurante.dto;

import com.wilson.braga.restaurante.model.CategoriaProduto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdutoDTO {

	private Long id;

	@NotBlank(message = "Nome do produto é obrigatório")
	private String nome;

	private String descricao;

	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser maior que zero")
	private Double preco;

	@NotNull(message = "Categoria é obrigatória")
	private CategoriaProduto categoria;

	private String imagem;

	private Boolean disponivel;

	@Min(value = 0, message = "Tempo de preparo não pode ser negativo")
	private Integer tempoPreparoMedio;

	private Integer totalVendas;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Long id, String nome, String descricao, Double preco, CategoriaProduto categoria, String imagem,
			Boolean disponivel, Integer tempoPreparoMedio, Integer totalVendas) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = categoria;
		this.imagem = imagem;
		this.disponivel = disponivel;
		this.tempoPreparoMedio = tempoPreparoMedio;
		this.totalVendas = totalVendas;
	}

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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
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

	public Integer getTempoPreparoMedio() {
		return tempoPreparoMedio;
	}

	public void setTempoPreparoMedio(Integer tempoPreparoMedio) {
		this.tempoPreparoMedio = tempoPreparoMedio;
	}

	public Integer getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(Integer totalVendas) {
		this.totalVendas = totalVendas;
	}
}
