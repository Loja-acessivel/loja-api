package br.com.apiloja.Repository;

import br.com.apiloja.Model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoIdOrderByIdAsc(Long pedidoId);

    List<ItemPedido> findByVendedorIdOrderByPedidoIdDescIdAsc(Long vendedorId);
}
