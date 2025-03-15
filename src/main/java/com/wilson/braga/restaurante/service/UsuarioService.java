package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wilson.braga.restaurante.dto.UsuarioDTO;
import com.wilson.braga.restaurante.model.Role;
import com.wilson.braga.restaurante.model.Usuario;
import com.wilson.braga.restaurante.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Cadastrar um novo usuário
	public UsuarioDTO cadastroUsuario(UsuarioDTO usuarioDTO) {
		// Verifica se o email já está em uso antes de cadastrar
		if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
			throw new RuntimeException("Email já está em uso.");
		}

		// Converte UsuarioDTO para Usuario usando o método toEntity
		Usuario usuario = toEntity(usuarioDTO);
		
		// Criptografa a senha antes de salvar
		String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
		usuario.setSenha(senhaCriptografada);

		// Salva o usuário no banco de dados
		Usuario usurioSalvo = usuarioRepository.save(usuario);

		// Retorna o usuário salvo como DTO
		return toDTO(usurioSalvo);
	}

	// Buscar usuário por ID
	public Optional<UsuarioDTO> buscarUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).map(this::toDTO);
	}

	// Listar os usuários com paginação
	public Page<UsuarioDTO> listarUsuarios(Pageable pageable) {
		Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);

		// Converte a lista de Usuario para UsuarioDTO
		List<UsuarioDTO> usuarioDTO = usuariosPage.getContent().stream().map(this::toDTO).collect(Collectors.toList());

		return new PageImpl<>(usuarioDTO, pageable, usuariosPage.getTotalElements());
	}

	// Buscar usuário por email
	public UsuarioDTO buscarUsuarioPorEmail(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

		return toDTO(usuario);
	}

	// Atualizar um usuário
	public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
		return usuarioRepository.findById(id).map(usuario -> {
			// Verifica se o email foi alterado
			if (!usuario.getEmail().equals(usuarioDTO.getEmail())) {
				// Verifica se o novo email já está em uso
				if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
					throw new RuntimeException("Email já está em uso por outro usuário.");
				}
			}

			// Atualiza os dados do usuário
			usuario.setNome(usuarioDTO.getNome());
			usuario.setEmail(usuarioDTO.getEmail());
			usuario.setRole(Role.valueOf(usuarioDTO.getRole()));
			
			// Atualiza a senha apenas se uma nova senha for fornecida
			if(usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
				usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha())); // Criptografa a nova senha
			}
			
			// Salva o usuário atualizado
			Usuario usuarioAtualizado = usuarioRepository.save(usuario);

			return toDTO(usuarioAtualizado);

		}).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}

	// Excluir um usuário
	public void excluirUsuario(Long id) {
		usuarioRepository.deleteById(id);
	}

	// Método auxiliar para converter Usuario em UsuarioDTO
	private UsuarioDTO toDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail());
		dto.setRole(usuario.getRole().toString());
		return dto;
	}

	// Método auxiliar para converter UsuarioDTO em Usuario
	private Usuario toEntity(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setRole(Role.valueOf(usuarioDTO.getRole())); // Converte String para Enum
		return usuario;
	}

}
