package felipe.proj.estqoue.controller;


import felipe.proj.estqoue.entidades.Fornecedor;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.response.ApiResponse;
import felipe.proj.estqoue.service.fornecedor.IForncedeorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    @Autowired
    private IForncedeorService forncedeorService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFornecedor(@RequestBody Fornecedor fornecedor){
        try {
            Fornecedor fornecedores = forncedeorService.addFornecedor(fornecedor);
            return ResponseEntity.ok(new ApiResponse("Fornecedor adicionado com sucesso: ", fornecedores));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> atualizarFornecedor(@RequestBody Fornecedor fornecedor, @PathVariable Long id){
        try {
            Fornecedor fornecedores = forncedeorService.atualizarFornecedor(fornecedor, id);
            return ResponseEntity.ok(new ApiResponse("Fornecedor atualizado com sucesso: ", fornecedores));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping ("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteFornecedor(@PathVariable Long id){
        try {
            forncedeorService.deleteFornecedor(id);
            return ResponseEntity.ok(new ApiResponse("Fornecedor deletado com sucesso: ", id));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllFornecedores() {
        List<Fornecedor> fornecedores = forncedeorService.getAllFornecedores();
        return  ResponseEntity.ok(new ApiResponse("Sucesso!", fornecedores));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFornecedorById(@PathVariable Long id){
        try {
            Fornecedor fornecedor = forncedeorService.getFornecedorById(id);
            return ResponseEntity.ok(new ApiResponse("De acordo com o id o produto é: ", fornecedor));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{nome}")
    public ResponseEntity<ApiResponse> getFornecedorByFornecedorNome(@PathVariable String nome){
        try {
            Optional<Fornecedor> fornecedores = forncedeorService.getFornecedorByFornecedorNome(nome);
            return ResponseEntity.ok(new ApiResponse("Fornecedor encontrado: ", fornecedores));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
