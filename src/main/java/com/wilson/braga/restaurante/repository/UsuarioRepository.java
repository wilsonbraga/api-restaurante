package com.wilson.braga.restaurante.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilson.braga.restaurante.dto.UsuarioDTO;
import com.wilson.braga.restaurante.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email); // Buscar usuário por email
	
	Page<Usuario> findAll(Pageable pageable); // Buscar Usuario por Paginação
	
	Optional<UsuarioDTO> buscarUsuarioPorId(Long id);

}
