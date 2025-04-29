package com.wilson.braga.restaurante.config;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wilson.braga.restaurante.model.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			var authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getPrincipal() instanceof Usuario usuario) {
				TenantContext.setCurrentTenant(usuario.getTenantId());
			} else {
				TenantContext.setCurrentTenant(null); // Caso não haja usuário autenticado
			}
			filterChain.doFilter(request, response);
		} finally {
			TenantContext.clear(); // Limpa o contexto após a requisição
		}
	}

}
