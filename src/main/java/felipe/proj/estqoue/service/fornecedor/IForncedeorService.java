package felipe.proj.estqoue.service.fornecedor;

import felipe.proj.estqoue.entidades.Fornecedor;

import java.util.List;
import java.util.Optional;

public interface IForncedeorService {
    Fornecedor addFornecedor(Fornecedor fornecedor);
    Fornecedor atualizarFornecedor(Fornecedor fornecedor, Long id);
    Fornecedor getFornecedorById(Long id);
    void deleteFornecedor(Long id);
    List<Fornecedor> getAllFornecedores();
    Optional<Fornecedor> getFornecedorByFornecedorNome(String fornecedorNome);
}
