package br.com.apiloja.Repository;

import br.com.apiloja.Model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho,Long> {
}
