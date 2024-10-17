package felipe.proj.estqoue.controller;

import felipe.proj.estqoue.entidades.Produto;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.request.AddProdutoRequest;
import felipe.proj.estqoue.request.AtualizarProdutoRequest;
import felipe.proj.estqoue.response.ApiResponse;
import felipe.proj.estqoue.service.produtos.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private IProdutoService produtoService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduto(@RequestBody AddProdutoRequest produto){
        try {
            Produto produtos = produtoService.addProduto(produto);
            return ResponseEntity.ok(new ApiResponse("Produto adicionado com sucesso: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping ("/{produtoId}/update")
    public ResponseEntity<ApiResponse> updateProduto(@RequestBody AtualizarProdutoRequest request, @PathVariable Long produtoId){
        try {
            Produto produtos = produtoService.updateProduto(request, produtoId);
            return ResponseEntity.ok(new ApiResponse("Produto atualizado com sucesso: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping ("/{produtoId}/delete")
    public ResponseEntity<ApiResponse> deleteProduto(@PathVariable Long produtoId){
        try {
            produtoService.deleteProduto(produtoId);
            return ResponseEntity.ok(new ApiResponse("Produto deletado com sucesso: ", produtoId));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProdutos() {
        List<Produto> produtos = produtoService.getAllProdutos();
        return  ResponseEntity.ok(new ApiResponse("Sucesso!", produtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProdutoById(@PathVariable Long id){
        try {
            Produto produtos = produtoService.getProdutoById(id);
            return ResponseEntity.ok(new ApiResponse("De acordo com o id o produto é: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<ApiResponse> getProdutoByNome(@PathVariable String nome){
        try {
            List<Produto> produtos = produtoService.getProdutoByNome(nome);
            return ResponseEntity.ok(new ApiResponse("Produto informada: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{categoria}")
    public ResponseEntity<ApiResponse> getProdutoByCategoria(@PathVariable String categoria){
        try {
            List<Produto> produtos = produtoService.getProdutoByCategoria(categoria);
            return ResponseEntity.ok(new ApiResponse("Produto informada: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{fornecedor}")
    public ResponseEntity<ApiResponse> getProdutoByFornecedor(@PathVariable String fornecedor){
        try {
            List<Produto> produtos = produtoService.getProdutoByFornecedor(fornecedor);
            return ResponseEntity.ok(new ApiResponse("Produto informada: ", produtos));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
