package com.wilson.braga.restaurante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wilson.braga.restaurante.dto.ProdutoDTO;
import com.wilson.braga.restaurante.model.Produto;
import com.wilson.braga.restaurante.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional(readOnly = true)
	public Page<ProdutoDTO> buscarProdutos(Pageable pageable) {
		Page<Produto> page = produtoRepository.findAll(pageable);
		return page.map(this::convertToDTO);
	}

	// Conversão entre DTO e Entidade
	private ProdutoDTO convertToDTO(Produto produto) {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(produto.getId());
		dto.setNome(produto.getNome());
		dto.setDescricao(produto.getDescricao());
		dto.setPreco(produto.getPreco());
		dto.setCategoria(produto.getCategoria());
		dto.setImagem(produto.getImagem());
		dto.setDisponivel(produto.getDisponivel());
		dto.setTempoPreparoMedio(produto.getTempoPreparoMedio());
		dto.setTotalVendas(produto.getTotalVendas());
		return dto;
	}

}
