package com.wilson.braga.restaurante.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wilson.braga.restaurante.dto.ProdutoDTO;
import com.wilson.braga.restaurante.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> buscarProdutos(
			@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
		Page<ProdutoDTO> produtos = produtoService.buscarProdutos(pageable);
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> salvarProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		ProdutoDTO novoProduto = produtoService.salvarProduto(produtoDTO);

		// Criar URI para o recurso criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoProduto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(novoProduto);
	}
	
	@GetMapping("/mais-vendidos")
	public ResponseEntity<Page<ProdutoDTO>> buscarProdutosMaisVendidos(@PageableDefault(size = 10) Pageable pageable) {
		Page<ProdutoDTO> produtosMaisVendidos = produtoService.buscarProdutosMaisVendidos(pageable);
		return ResponseEntity.ok(produtosMaisVendidos);
	}
	
	@GetMapping("/top10-mais-vendidos")
	public ResponseEntity<List<ProdutoDTO>> buscarTop10ProdutosMaisVendidos() {
		List<ProdutoDTO> top10Produtos = produtoService.buscarTop10ProdutosMaisVendidos();
		return ResponseEntity.ok(top10Produtos);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id,
			@Valid @RequestBody ProdutoDTO produtoDTO) {
		ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
		return ResponseEntity.ok(produtoAtualizado);
	}


}
