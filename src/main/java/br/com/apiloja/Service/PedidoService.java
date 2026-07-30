package br.com.apiloja.Service;

import br.com.apiloja.Dto.Pedido.ItemPedidoRequestDTO;
import br.com.apiloja.Dto.Pedido.ItemPedidoResponseDTO;
import br.com.apiloja.Dto.Pedido.PedidoRequestDTO;
import br.com.apiloja.Dto.Pedido.PedidoResponseDTO;
import br.com.apiloja.Dto.Pedido.PedidoVendedorResponseDTO;
import br.com.apiloja.Model.ItemPedido;
import br.com.apiloja.Model.Pedido;
import br.com.apiloja.Model.Produto;
import br.com.apiloja.Model.Usuario;
import br.com.apiloja.Repository.ItemPedidoRepository;
import br.com.apiloja.Repository.PedidoRepository;
import br.com.apiloja.Repository.ProdutoRepository;
import br.com.apiloja.Repository.UsuarioRepository;
import br.com.apiloja.Repository.VendedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ItemPedidoRepository itemPedidoRepo;
    private final ProdutoRepository produtoRepo;
    private final UsuarioRepository usuarioRepo;
    private final VendedorRepository vendedorRepo;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        validarPedido(dto);

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuário não encontrado com o ID: " + dto.getUsuarioId()));

        Map<Long, Integer> quantidadesPorProduto = agruparQuantidades(dto.getItens());
        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entrada : quantidadesPorProduto.entrySet()) {
            Produto produto = produtoRepo.findById(entrada.getKey())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Produto não encontrado com o ID: " + entrada.getKey()));
            int quantidade = entrada.getValue();

            if (!"disponivel".equalsIgnoreCase(produto.getStatus()) || produto.getEstoque() <= 0) {
                throw new IllegalArgumentException(
                        "O produto " + produto.getNome() + " não está disponível.");
            }
            if (quantidade > produto.getEstoque()) {
                throw new IllegalArgumentException(
                        "A quantidade solicitada de " + produto.getNome() + " ultrapassa o estoque disponível.");
            }

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
            total = total.add(subtotal);

            ItemPedido item = new ItemPedido();
            item.setProdutoId(produto.getId());
            item.setVendedorId(produto.getVendedorId());
            item.setProdutoNome(produto.getNome());
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(produto.getPreco());
            item.setSubtotal(subtotal);
            itens.add(item);
        }

        LocalDateTime agora = LocalDateTime.now();
        Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuario.getId());
        pedido.setCompradorNome(usuario.getNome());
        pedido.setCompradorEmail(usuario.getEmail());
        pedido.setStatus("recebido");
        pedido.setTotal(total);
        pedido.setCriadoEm(agora);
        pedido.setAtualizadoEm(agora);
        pedido = pedidoRepo.save(pedido);

        Long pedidoId = pedido.getId();
        itens.forEach(item -> item.setPedidoId(pedidoId));
        itens = itemPedidoRepo.saveAll(itens);

        return paraResponse(pedido, itens);
    }

    public List<PedidoVendedorResponseDTO> buscarPorVendedor(Long vendedorId) {
        if (!vendedorRepo.existsById(vendedorId)) {
            throw new EntityNotFoundException("Vendedor não encontrado com o ID: " + vendedorId);
        }

        List<ItemPedido> itensDoVendedor =
                itemPedidoRepo.findByVendedorIdOrderByPedidoIdDescIdAsc(vendedorId);
        Map<Long, List<ItemPedido>> itensPorPedido = new LinkedHashMap<>();
        itensDoVendedor.forEach(item ->
                itensPorPedido.computeIfAbsent(item.getPedidoId(), chave -> new ArrayList<>()).add(item));

        return itensPorPedido.entrySet().stream()
                .map(entrada -> {
                    Pedido pedido = pedidoRepo.findById(entrada.getKey())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Pedido não encontrado com o ID: " + entrada.getKey()));
                    List<ItemPedidoResponseDTO> itens = entrada.getValue().stream()
                            .map(this::paraItemResponse)
                            .toList();
                    BigDecimal subtotalVendedor = entrada.getValue().stream()
                            .map(ItemPedido::getSubtotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new PedidoVendedorResponseDTO(
                            pedido.getId(),
                            pedido.getCompradorNome(),
                            pedido.getCompradorEmail(),
                            pedido.getStatus(),
                            subtotalVendedor,
                            pedido.getCriadoEm(),
                            itens
                    );
                })
                .toList();
    }

    private void validarPedido(PedidoRequestDTO dto) {
        if (dto == null || dto.getUsuarioId() == null) {
            throw new IllegalArgumentException("O usuarioId é obrigatório.");
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

    private PedidoResponseDTO paraResponse(Pedido pedido, List<ItemPedido> itens) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getUsuarioId(),
                pedido.getCompradorNome(),
                pedido.getCompradorEmail(),
                pedido.getStatus(),
                pedido.getTotal(),
                pedido.getCriadoEm(),
                itens.stream().map(this::paraItemResponse).toList()
        );
    }

    private ItemPedidoResponseDTO paraItemResponse(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getProdutoId(),
                item.getVendedorId(),
                item.getProdutoNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }
}
