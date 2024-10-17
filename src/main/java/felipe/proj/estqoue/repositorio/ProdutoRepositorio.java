package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    List<Produto> findByNome(String nome);
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByFornecedorFornecedorNome(String fornecedor);
}
