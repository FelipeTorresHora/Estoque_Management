package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.Fornecedor;
import felipe.proj.estqoue.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepositorio extends JpaRepository<Venda, Long> {
}
