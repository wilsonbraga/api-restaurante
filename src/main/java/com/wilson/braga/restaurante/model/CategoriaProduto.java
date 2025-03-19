package com.wilson.braga.restaurante.model;

public enum CategoriaProduto {
	BEBIDA("Bebida"),
    PRATO_PRINCIPAL("Prato Principal"),
    SOBREMESA("Sobremesa");

    private final String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
