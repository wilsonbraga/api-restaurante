package com.wilson.braga.restaurante.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wilson.braga.restaurante.dto.ItemPedidoDTO;
import com.wilson.braga.restaurante.dto.RelatorioProdutoDTO;
import com.wilson.braga.restaurante.model.ItemPedido;
import com.wilson.braga.restaurante.model.Pedido;
import com.wilson.braga.restaurante.model.Produto;
import com.wilson.braga.restaurante.projection.RelatorioProdutoProjection;
import com.wilson.braga.restaurante.repository.ItemPedidoRepository;
import com.wilson.braga.restaurante.repository.PedidoRepository;
import com.wilson.braga.restaurante.repository.ProdutoRepository;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Transactional(readOnly = true)
	public List<ItemPedidoDTO> findByPedidoId(Long pedidoId) {
		List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(pedidoId);
		return itens.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ItemPedidoDTO findById(Long id) {
		ItemPedido item = itemPedidoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado com ID: " + id));
		return convertToDTO(item);
	}

	@Transactional
	public ItemPedidoDTO save(ItemPedidoDTO itemDTO, Long pedidoId) {
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado com ID: " + pedidoId));

		Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Produto não encontrado com ID: " + itemDTO.getProdutoId()));

		// VERIFICA SE O PRODUTO ESTÁ DISPONÍVEL
		if (!produto.getDisponivel()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Não temos o Produto " + produto.getNome() + " no momento, escolha outra opção.");
		}

		ItemPedido item = new ItemPedido();
		item.setPedido(pedido);
		item.setProduto(produto); // ISSO TAMBÉM DEFINE O PREÇO UNITÁRIO
		item.setQuantidade(itemDTO.getQuantidade());

		ItemPedido savaItem = itemPedidoRepository.save(item);

		// ATUALIZAR ESTATÍSTICAS DE VENDAS
		produtoService.incrementarVendas(produto.getId(), item.getQuantidade());

		return convertToDTO(savaItem);
	}

	@Transactional
	public ItemPedidoDTO update(Long id, ItemPedidoDTO itemDTO) {

		ItemPedido existeItem = itemPedidoRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Pedido não encontrado com ID: " + id));

		// SE O PRODUTO FOR ALTERADO, VERIFICAR DISPONIBILIDADE
		if (!itemDTO.getProdutoId().equals(existeItem.getProduto().getId())) {

			Produto novoProduto = produtoRepository.findById(itemDTO.getProdutoId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Produto não encontrado com ID: " + itemDTO.getProdutoId()));

			if (!novoProduto.getDisponivel()) {
				throw new ResponseStatusException(HttpStatus.CONFLICT,
						"Não temos o Produto " + novoProduto.getNome() + " no momento, escolha outra opção.");
			}
			// ATUALIZAR ESTATÍSTICAS (DECREMENTAR O ANTIGO PRODUTO)
			produtoService.incrementarVendas(novoProduto.getId(), itemDTO.getQuantidade());
		} else {
			// AJUSTAR ESTATÍSTICAS SE SÓ A QUANTIDADE MUDOU
			int diferenca = itemDTO.getQuantidade() - existeItem.getQuantidade();
			if (diferenca != 0) {
				produtoService.incrementarVendas(existeItem.getProduto().getId(), diferenca);
			}
		}
		existeItem.setQuantidade(itemDTO.getQuantidade());

		ItemPedido itemAtualizado = itemPedidoRepository.save(existeItem);
		return convertToDTO(itemAtualizado);
	}

	// mostra quais produtos vendem mais
	@Transactional(readOnly = true)
	public Page<RelatorioProdutoDTO> getItensMaisVendidos(Pageable pageable) {
		Page<RelatorioProdutoProjection> itensMaisVendidos = itemPedidoRepository.findMostOrderedItems(pageable);

		return itensMaisVendidos.map(projection -> {
			RelatorioProdutoDTO dto = new RelatorioProdutoDTO();
			dto.setProdutoId(projection.getProdutoId());
			dto.setNomeProduto(projection.getNomeProduto());
			dto.setQuantidadeTotal(projection.getQuantidadeTotal());
			dto.setValorTotal(projection.getValorTotal());
			return dto;
		});
	}

	// Conversão entre DTO e Entidade
	private ItemPedidoDTO convertToDTO(ItemPedido item) {
		ItemPedidoDTO dto = new ItemPedidoDTO();
		dto.setId(item.getId());
		dto.setPedidoId(item.getPedido().getId());
		dto.setProdutoId(item.getProduto().getId());
		dto.setNomeProduto(item.getProduto().getNome());
		dto.setQuantidade(item.getQuantidade());
		dto.setPrecoUnitario(item.getPrecoUnitario());
		dto.setSubtotal(item.getSubtotatal());
		return dto;
	}

}
