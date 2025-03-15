package com.wilson.braga.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilson.braga.restaurante.model.Usuario;
import com.wilson.braga.restaurante.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public Page<Usuario> listarUsuarios(Pageable pageable){
		return usuarioService.listarUsuarios(pageable);
	}

}
