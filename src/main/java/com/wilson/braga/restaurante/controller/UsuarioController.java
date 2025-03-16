package com.wilson.braga.restaurante.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wilson.braga.restaurante.dto.UsuarioDTO;
import com.wilson.braga.restaurante.exception.OrdenacaoInvalidaException;
import com.wilson.braga.restaurante.exception.TamanhoPaginaInvalidoException;
import com.wilson.braga.restaurante.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	// metodo lista com paginacao, fiz uma abordagem híbrida
	// limitei o size no back-end mas permite que o page e sort sejam passados pelo
	// front-end

	@GetMapping
	public Page<UsuarioDTO> listarUsuarios(@RequestParam(defaultValue = "0") int page, // Página atual (padrão: 0)
			@RequestParam(required = false) Integer size, // Tamanho da página (opcional)
			@RequestParam(required = false) String sort) { // Ordenação (opcional)

		// Validação do tamanho da página (size)
		int maxPageSize = 50;
		if (size != null && (size <= 0 || size > maxPageSize)) {
			throw new TamanhoPaginaInvalidoException(
					"O tamanho da página deve ser maior que 0 e menor ou igual a " + maxPageSize + ".");
		}

		int pageSize = (size != null) ? size : 10;

		// Configura a ordenação

		Sort.Direction direction = Sort.Direction.ASC; // Direção padrão: ascendente
		String property = "nome"; // Propriedade padrão para ordenação

		if (sort != null) {
			String[] sortParams = sort.split(",");
			if (sortParams.length != 2 || !Arrays.asList("asc", "desc").contains(sortParams[1].toLowerCase())) {
				throw new OrdenacaoInvalidaException(
						"O parâmetro de ordenação deve estar no formato 'campo,direção' (ex: 'nome,asc').");
			}
			direction = Sort.Direction.fromString(sortParams[1]); // Direção (asc ou desc)
			property = sortParams[0]; // Propriedade para ordenação
		}
		// Cria o objeto Pageable
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, property));

		// Retorna os usuários paginados
		return usuarioService.listarUsuarios(pageable);
	}

	// Endpoint para cadastrar um novo usuário
	@PostMapping("/cadastro")
	public ResponseEntity<UsuarioDTO> cadastroUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		// Chama o serviço para cadastrar o usuário
		UsuarioDTO usuarioSalvo = usuarioService.cadastroUsuario(usuarioDTO);

		// Retorna o usuário cadastrado com status HTTP 201 (Created)
		return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);

	}

}
