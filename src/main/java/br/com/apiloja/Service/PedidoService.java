package br.com.apiloja.Service;

import br.com.apiloja.Dto.Pedido.ItemPedidoRequestDTO;
import br.com.apiloja.Dto.Pedido.PedidoRequestDTO;
import br.com.apiloja.Dto.Pedido.PedidoResponseDTO;
import br.com.apiloja.Dto.Pedido.PedidoVendedorResponseDTO;
import br.com.apiloja.Model.Pedido;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.PedidoRepository;
import br.com.apiloja.Repository.ProdutoRepository;
import br.com.apiloja.Repository.UsuarioRepository;
import br.com.apiloja.Repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final UsuarioRepository usuarioRepo;
    private final VendedorRepository vendedorRepo;

    @Transactional
    public List<PedidoResponseDTO> criar(PedidoRequestDTO dto) {
        validarPedido(dto);

        Usuario comprador = usuarioRepo.findById(dto.getCompradorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comprador não encontrado com o ID: " + dto.getCompradorId()));

        Map<Long, Integer> quantidadesPorProduto = agruparQuantidades(dto.getItens());
        List<Pedido> linhasDoPedido = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();

        for (Map.Entry<Long, Integer> entrada : quantidadesPorProduto.entrySet()) {
            Produto produto = produtoRepo.findById(entrada.getKey())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não encontrado com o ID: " + entrada.getKey()));
            int quantidade = entrada.getValue();

            validarDisponibilidade(produto, quantidade);

            for (int unidade = 0; unidade < quantidade; unidade += 1) {
                Pedido pedido = new Pedido();
                pedido.setCompradorId(comprador.getId());
                pedido.setProdutoId(produto.getId());
                pedido.setStatus("recebido");
                pedido.setTotal(produto.getPreco());
                pedido.setCriadoEm(agora);
                pedido.setAtualizadoEm(agora);
                linhasDoPedido.add(pedido);
            }
        }

        return pedidoRepo.saveAll(linhasDoPedido).stream()
                .map(this::paraResponse)
                .toList();
    }

    public List<PedidoVendedorResponseDTO> buscarPorVendedor(Long vendedorId) {
        if (!vendedorRepo.existsById(vendedorId)) {
            throw new EntityNotFoundException("Vendedor não encontrado com o ID: " + vendedorId);
        }

        List<Produto> produtosDoVendedor = produtoRepo.findByVendedorId(vendedorId);
        if (produtosDoVendedor.isEmpty()) {
            return List.of();
        }

        Map<Long, Produto> produtosPorId = produtosDoVendedor.stream()
                .collect(Collectors.toMap(Produto::getId, Function.identity()));
        List<Pedido> pedidos = pedidoRepo.findByProdutoIdInOrderByCriadoEmDescIdDesc(
                produtosPorId.keySet());

        Map<Long, Usuario> compradoresPorId = usuarioRepo.findAllById(
                        pedidos.stream().map(Pedido::getCompradorId).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Usuario::getId, Function.identity()));

        return pedidos.stream()
                .map(pedido -> paraVendedorResponse(
                        pedido,
                        compradoresPorId.get(pedido.getCompradorId()),
                        produtosPorId.get(pedido.getProdutoId())))
                .toList();
    }

    private void validarPedido(PedidoRequestDTO dto) {
        if (dto == null || dto.getCompradorId() == null) {
            throw new IllegalArgumentException("O compradorId é obrigatório.");
        }
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve possuir pelo menos um item.");
        }
    }

    private Map<Long, Integer> agruparQuantidades(List<ItemPedidoRequestDTO> itens) {
        Map<Long, Integer> agrupados = new LinkedHashMap<>();

        for (ItemPedidoRequestDTO item : itens) {
            if (item == null || item.getProdutoId() == null) {
                throw new IllegalArgumentException("Todos os itens devem informar o produtoId.");
            }
            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade dos itens deve ser maior que zero.");
            }
            agrupados.merge(item.getProdutoId(), item.getQuantidade(), Integer::sum);
        }
        return agrupados;
    }

    private void validarDisponibilidade(Produto produto, int quantidade) {
        if (!"disponivel".equalsIgnoreCase(produto.getStatus()) || produto.getEstoque() <= 0) {
            throw new IllegalArgumentException(
                    "O produto " + produto.getNome() + " não está disponível.");
        }
        if (quantidade > produto.getEstoque()) {
            throw new IllegalArgumentException(
                    "A quantidade solicitada de " + produto.getNome()
                            + " ultrapassa o estoque disponível.");
        }
    }

    private PedidoResponseDTO paraResponse(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCompradorId(),
                pedido.getProdutoId(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm()
        );
    }

    private PedidoVendedorResponseDTO paraVendedorResponse(
            Pedido pedido,
            Usuario comprador,
            Produto produto
    ) {
        if (comprador == null) {
            throw new EntityNotFoundException(
                    "Comprador não encontrado com o ID: " + pedido.getCompradorId());
        }
        if (produto == null) {
            throw new EntityNotFoundException(
                    "Produto não encontrado com o ID: " + pedido.getProdutoId());
        }

        return new PedidoVendedorResponseDTO(
                pedido.getId(),
                comprador.getId(),
                comprador.getNome(),
                comprador.getEmail(),
                comprador.getTelefone(),
                comprador.getEndereco(),
                produto.getId(),
                produto.getNome(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm()
        );
    }
}
