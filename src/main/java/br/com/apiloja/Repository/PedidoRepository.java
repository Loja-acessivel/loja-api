package br.com.apiloja.Repository;

import br.com.apiloja.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByProdutoIdInOrderByCriadoEmDescIdDesc(Collection<Long> produtoIds);
}
