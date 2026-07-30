package br.com.apiloja.Repository;

import br.com.apiloja.Model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    Optional<Vendedor> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
