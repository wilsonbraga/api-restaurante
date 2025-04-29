package com.wilson.braga.restaurante.config;

public class TenantContext {

	private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public static String getCurrenttenant() {
		return currentTenant.get();
	}

	public static void setCurrentTenant(String tenantId) {
		currentTenant.set(tenantId);
	}

	public static void clear() {
		currentTenant.remove();
	}
}
