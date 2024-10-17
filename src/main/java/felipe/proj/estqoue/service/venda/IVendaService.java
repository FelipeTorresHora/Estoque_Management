package felipe.proj.estqoue.service.venda;

import felipe.proj.estqoue.entidades.Venda;

import java.util.List;
import java.util.Optional;

public interface IVendaService {
    Venda addVenda(Venda venda);
    Venda getVendaById(Long id);
    Venda atualizarVenda(Long id, Venda vendaAtualizada);
    void deleteVenda(Long id);
    List<Venda> getAllVenda();
}
