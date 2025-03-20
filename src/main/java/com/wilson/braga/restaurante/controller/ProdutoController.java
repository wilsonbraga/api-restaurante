package com.wilson.braga.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilson.braga.restaurante.dto.ProdutoDTO;
import com.wilson.braga.restaurante.service.ProdutoService;

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

	
	

}
