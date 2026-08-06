package br.com.apiloja.Repository;

import br.com.apiloja.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByVendedorId(Long vendedorId);
}
