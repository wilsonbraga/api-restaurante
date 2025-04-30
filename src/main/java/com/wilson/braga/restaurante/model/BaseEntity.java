package com.wilson.braga.restaurante.model;

import java.io.Serializable;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "tenant_id", nullable = false)
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
