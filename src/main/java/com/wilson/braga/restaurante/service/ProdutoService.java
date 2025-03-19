package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wilson.braga.restaurante.dto.ProdutoDTO;
import com.wilson.braga.restaurante.model.CategoriaProduto;
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

	public List<ProdutoDTO> buscarTop10ProdutosMaisVendidos() {
		List<Produto> produtosTop10 = produtoRepository.findTop10ByOrderByTotalVendasDesc();
		return produtosTop10.stream().map(this::convertToDTO).collect(Collectors.toList());

	}

	@Transactional
	public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
		Produto produto = convertToEntity(produtoDTO);

		// Definindo valores padrão se necessário
		if (produto.getDisponivel() == null) {
			produto.setDisponivel(true);
		}

		if (produto.getTotalVendas() <= 0) {
			produto.setTotalVendas(0);
		}

		Produto produtoSalvo = produtoRepository.save(produto);
		return convertToDTO(produtoSalvo);
	}

	public Page<ProdutoDTO> buscarProdutosMaisVendidos(Pageable pageable) {
		Page<Produto> litaProdutos = produtoRepository.findAllByOrderByTotalVendasDesc(pageable);
		return litaProdutos.map(produto -> convertToDTO(produto));
	}

	@Transactional
	public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
		// Verificar se o produto existe
		Produto existingProduto = produtoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com id: " + id));
		// Atualizar todos os campos exceto o ID e totalVendas
		existingProduto.setNome(produtoDTO.getNome());
		existingProduto.setDescricao(produtoDTO.getDescricao());
		existingProduto.setPreco(produtoDTO.getPreco());
		existingProduto.setCategoria(produtoDTO.getCategoria());
		existingProduto.setImagem(produtoDTO.getImagem());
		existingProduto.setDisponivel(produtoDTO.getDisponivel());
		existingProduto.setTempoPreparoMedio(produtoDTO.getTempoPreparoMedio());

		// Não atualizar totalVendas diretamente por motivos de segurança

		Produto produtoAtualizado = produtoRepository.save(existingProduto);
		return convertToDTO(produtoAtualizado);
	}

	public Page<ProdutoDTO> buscarPorCategoria(CategoriaProduto categotia, Pageable pageable) {
		Page<Produto> listaCateria = produtoRepository.findByCategoria(categotia, pageable);
		return listaCateria.map(this::convertToDTO);
		// TODO: FAZER A VALIDACAO DE CATEGORIA E Pageable
	}

	@Transactional
	public void excluir(Long id) {
		if (!produtoRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com id: " + id);
		}

		produtoRepository.deleteById(id);
	}

	@Transactional
	public ProdutoDTO atualizarProdutoDisponivel(Long id, boolean disponivel) {
		Produto produto = produtoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com id: " + id));

		produto.setDisponivel(disponivel);

		Produto produtoAtualizado = produtoRepository.save(produto);
		return convertToDTO(produtoAtualizado);
	}

	@Transactional
	public void incrementarVendas(Long id, int quantidade) {
		
		Produto produto = produtoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com id: " + id));

		produto.setTotalVendas(produto.getTotalVendas() + quantidade);
		produtoRepository.save(produto);
	}

	@SuppressWarnings("unused")
	private Produto convertToEntity(ProdutoDTO dto) {
		Produto entity = new Produto();
		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setPreco(dto.getPreco());
		entity.setCategoria(dto.getCategoria());
		entity.setImagem(dto.getImagem());
		entity.setDisponivel(dto.getDisponivel());
		entity.setTempoPreparoMedio(dto.getTempoPreparoMedio());
		// Não coiei totalVendas do DTO para a entidade por segurança
		return entity;
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
