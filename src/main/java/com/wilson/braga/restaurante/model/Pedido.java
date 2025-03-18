package com.wilson.braga.restaurante.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "mesa_id")
	private Mesa mesa;

	private String cliente;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusPedido status;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;

	@ManyToOne
	@JoinColumn(name = "garcom_id")
	private Usuario garcom;

	@Column(nullable = false)
	private LocalDateTime dataCriacao;

	@Column
	private LocalDateTime dataAtualizacao;

	@Column
	private LocalDateTime dataPreparo;

	@Column
	private LocalDateTime dataPronto;

	@Column
	private LocalDateTime dataEntrega;

	@Column
	private String observacoes;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAtendimento tipoAtendimento;

	private Integer tempoPreparo; // em minutos

	@PrePersist
	public void prePersist() {
		this.dataCriacao = LocalDateTime.now();
		if (this.status == null) {
			this.status = StatusPedido.EM_PREPARO;
		}

		if (this.status == StatusPedido.EM_PREPARO) {
			this.dataPreparo = this.dataCriacao;
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.dataAtualizacao = LocalDateTime.now();

		// Atualiza os timestamps baseados no status atual
		if (this.status == StatusPedido.PRONTO && this.dataPronto == null) {
			this.dataPronto = this.dataAtualizacao;
		} else if (this.status == StatusPedido.ENTREGUE && this.dataEntrega == null) {
			this.dataEntrega = this.dataAtualizacao;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public Usuario getGarcom() {
		return garcom;
	}

	public void setGarcom(Usuario garcom) {
		this.garcom = garcom;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public LocalDateTime getDataPreparo() {
		return dataPreparo;
	}

	public void setDataPreparo(LocalDateTime dataPreparo) {
		this.dataPreparo = dataPreparo;
	}

	public LocalDateTime getDataPronto() {
		return dataPronto;
	}

	public void setDataPronto(LocalDateTime dataPronto) {
		this.dataPronto = dataPronto;
	}

	public LocalDateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Double getTotal() {
		return itens.stream().mapToDouble(item -> item.getQuantidade() * item.getPrecoUnitario()).sum();

	}
	

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public Integer getTempoPreparo() {
		return tempoPreparo;
	}

	public void setTempoPreparo(Integer tempoPreparo) {
		this.tempoPreparo = tempoPreparo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(id, other.id);
	}

}
