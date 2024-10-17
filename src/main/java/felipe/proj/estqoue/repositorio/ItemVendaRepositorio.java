package felipe.proj.estqoue.repositorio;

import felipe.proj.estqoue.entidades.ItemVenda;
import felipe.proj.estqoue.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {
}
