package com.wilson.braga.restaurante.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TenantHibernateFilter tenantHibernateFilter;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String tenantId = TenantContext.getCurrentTenant();
		if (tenantId != null) {
			tenantHibernateFilter.enableTenantFilter(tenantId);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		tenantHibernateFilter.disableTenantFilter();
	}
}
