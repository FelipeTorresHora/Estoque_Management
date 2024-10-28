package felipe.proj.estqoue.service.produtos;

import felipe.proj.estqoue.entidades.Fornecedor;
import felipe.proj.estqoue.entidades.Produto;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.repositorio.FornecedorRepositorio;
import felipe.proj.estqoue.repositorio.ProdutoRepositorio;
import felipe.proj.estqoue.request.AddProdutoRequest;
import felipe.proj.estqoue.request.AtualizarProdutoRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProdutoService implements IProdutoService {

    private final FornecedorRepositorio fornecedorRepositorio;
    private final ProdutoRepositorio produtoRepositorio;

    public ProdutoService(FornecedorRepositorio fornecedorRepositorio, ProdutoRepositorio produtoRepositorio) {
        this.fornecedorRepositorio = fornecedorRepositorio;
        this.produtoRepositorio = produtoRepositorio;
    }

    @Override
    public Produto addProduto(AddProdutoRequest request)  {
        // Buscar ou criar o fornecedor com base no nome
        Fornecedor fornecedor = fornecedorRepositorio
                .findByFornecedorNome(request.getFornecedor().getFornecedorNome())
                .orElseGet(() -> {
                    Fornecedor newFornecedor = new Fornecedor();
                    newFornecedor.setFornecedorNome(request.getFornecedor().getFornecedorNome());
                    newFornecedor.setNumero(request.getFornecedor().getNumero());
                    newFornecedor.setAvaliacao(request.getFornecedor().getAvaliacao());
                    return fornecedorRepositorio.save(newFornecedor);
                });

        request.setFornecedor(fornecedor);
        Set<Fornecedor> fornecedores = new HashSet<>();
        fornecedores.add(fornecedor);
        validarProduto(request);
        Produto produto = criarProduto(request, fornecedores);

        return produtoRepositorio.save(produto);
    }

    private Produto criarProduto(AddProdutoRequest request, Set<Fornecedor> fornecedores){
        return new Produto(
                request.getNome(),
                request.getQuantidade(),
                request.getPrecoCompra(),
                request.getPrecoVenda(),
                request.getCategoria(),
                request.getDataCompra(),
                request.getDataValidade(),
                fornecedores);
    }

    private void validarProduto(AddProdutoRequest request) {
        if (request.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        if (request.getPrecoCompra() <= 0) {
            throw new IllegalArgumentException("O preço de compra deve ser positivo.");
        }
        if (request.getPrecoVenda() <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser positivo.");
        }
        if (request.getPrecoVenda() < request.getPrecoCompra()) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que o preço de compra.");
        }
        if (request.getDataValidade() != null && request.getDataValidade().isBefore(request.getDataCompra())) {
            throw new IllegalArgumentException("A data de validade não pode ser anterior à data de compra.");
        }
    }


    @Override
    public Produto updateProduto(AtualizarProdutoRequest request, Long produtoId) {
        return produtoRepositorio.findById(produtoId)
                .map(produtoExistente -> upprodutoExistente(produtoExistente, request))
                .map(produtoRepositorio :: save)
                .orElseThrow(()-> new GlobalExcecao("Produto não encontrado!!"));
    }

    private Produto upprodutoExistente(Produto produtoExistente, AtualizarProdutoRequest request) {
        produtoExistente.setNome(request.getNome());
        produtoExistente.setQuantidade(request.getQuantidade());
        produtoExistente.setPrecoCompra(request.getPrecoCompra());
        produtoExistente.setPrecoVenda(request.getPrecoVenda());
        produtoExistente.setCategoria(request.getCategoria());
        produtoExistente.setDataCompra(request.getDataCompra());
        produtoExistente.setDataValidade(request.getDataValidade());

        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findByFornecedorNome(request.getFornecedor().getFornecedorNome());
        return produtoExistente;
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepositorio.findById(id)
                .ifPresentOrElse(produtoRepositorio::delete,() -> {
                    throw new GlobalExcecao("Não foi possivel encontrar esse produto!");
                });
    }

    @Override
    public Produto getProdutoById(Long id) {
        return produtoRepositorio.findById(id)
                .orElseThrow(()-> new GlobalExcecao("Não foi possivel encontrar esse produto!"));
    }

    @Override
    public List<Produto> getAllProdutos() {
        return produtoRepositorio.findAll();
    }

    @Override
    public List<Produto> getProdutoByNome(String nome) {
        return produtoRepositorio.findByNome(nome);
    }

    @Override
    public List<Produto> getProdutoByCategoria(String categoria) {
        return produtoRepositorio.findByCategoria(categoria);
    }

    @Override
    public List<Produto> getProdutoByFornecedor(String fornecedorNome) {
        return produtoRepositorio.findByFornecedorFornecedorNome(fornecedorNome);
    }
}
