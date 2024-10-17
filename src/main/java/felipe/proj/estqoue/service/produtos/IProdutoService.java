package felipe.proj.estqoue.service.produtos;

import felipe.proj.estqoue.entidades.Produto;
import felipe.proj.estqoue.request.AddProdutoRequest;
import felipe.proj.estqoue.request.AtualizarProdutoRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProdutoService {
    Produto addProduto(AddProdutoRequest produto);
    Produto updateProduto(AtualizarProdutoRequest produto, Long produtoId);
    Produto getProdutoById(Long id);
    void deleteProduto(Long id);
    List<Produto> getAllProdutos();
    List<Produto> getProdutoByNome(String nome);
    List<Produto> getProdutoByCategoria(String categoria);
    List<Produto> getProdutoByFornecedor(String fornecedor);
}
