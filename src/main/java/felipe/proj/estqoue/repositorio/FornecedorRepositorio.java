package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepositorio extends JpaRepository<Fornecedor, Long> {
    Optional<Fornecedor> findByFornecedorNome(String fornecedorNome);

    boolean existsByFornecedorNome(String fornecedorNome);

}
