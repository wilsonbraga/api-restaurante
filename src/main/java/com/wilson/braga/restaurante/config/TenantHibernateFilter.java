package com.wilson.braga.restaurante.config;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.hibernate.Filter;
import org.hibernate.Session;

@Component
public class TenantHibernateFilter {

	@PersistenceContext
	private EntityManager entityManager;

	public void enableTenantFilter(String tenantId) {
		
		Session session = entityManager.unwrap(Session.class);
		Filter filter = session.enableFilter("tenantFilter");
		filter.setParameter("tenantId", tenantId);
		filter.validate();
	}

	public void disableTenantFilter() {
		
		Session session = entityManager.unwrap(Session.class);
		session.disableFilter("tenantFilter");
	}

}
