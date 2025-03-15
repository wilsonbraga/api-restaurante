package com.wilson.braga.restaurante.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wilson.braga.restaurante.model.Usuario;
import com.wilson.braga.restaurante.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Cadastrar um novo usuário
	public Usuario cadastroUsuario(Usuario usuario) {
		// Verifica se o email já está em uso antes de cadastrar
		if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
			throw new RuntimeException("Email já está em uso.");
		}
		return usuarioRepository.save(usuario);
	}

	// Buscar usuário por ID
	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	// Listar os usuários com paginação
	public Page<Usuario> listarUsuarios(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	// Buscar usuário por email
	public Usuario buscarUsuarioPorEmail(String email) {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}

	// Atualizar um usuário
	public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
		return usuarioRepository.findById(id).map(usuario -> {
			// Verifica se o email foi alterado
			if (!usuario.getEmail().equals(usuarioAtualizado.getEmail())) {
				// Verifica se o novo email já está em uso
				if (usuarioRepository.findByEmail(usuarioAtualizado.getEmail()).isPresent()) {
					throw new RuntimeException("Email já está em uso por outro usuário.");
				}
			}

			// Atualiza os dados do usuário
			usuario.setNome(usuarioAtualizado.getNome());
			usuario.setEmail(usuarioAtualizado.getEmail());
			usuario.setSenha(usuarioAtualizado.getSenha());
			usuario.setRole(usuarioAtualizado.getRole());

			// Salva o usuário atualizado
			return usuarioRepository.save(usuario);
		}).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}

	// Excluir um usuário
	public void excluirUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

}
