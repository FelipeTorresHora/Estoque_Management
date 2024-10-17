package felipe.proj.estqoue.controller;

import felipe.proj.estqoue.entidades.Fornecedor;
import felipe.proj.estqoue.entidades.Venda;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.response.ApiResponse;
import felipe.proj.estqoue.service.venda.IVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    @Autowired
    private IVendaService vendaService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVenda(@RequestBody Venda venda){
        try {
            Venda vendas = vendaService.addVenda(venda);
            return ResponseEntity.ok(new ApiResponse("Produto adicionado com sucesso: ", vendas));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping ("/{produtoId}/update")
    public ResponseEntity<ApiResponse> atualizarVenda(@PathVariable Long id,@RequestBody Venda venda){
        try {
            Venda vendas = vendaService.atualizarVenda(id,venda);
            return ResponseEntity.ok(new ApiResponse("Produto atualizado com sucesso: ", vendas));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping ("/{produtoId}/delete")
    public ResponseEntity<ApiResponse> deleteVenda(@PathVariable Long produtoId){
        try {
            vendaService.deleteVenda(produtoId);
            return ResponseEntity.ok(new ApiResponse("Produto deletado com sucesso: ", produtoId));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllVenda() {
        List<Venda> venda = vendaService.getAllVenda();
        return  ResponseEntity.ok(new ApiResponse("Sucesso!", venda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVendaById(@PathVariable Long id){
        try {
            Venda venda = vendaService.getVendaById(id);
            return ResponseEntity.ok(new ApiResponse("De acordo com o id o produto é: ", venda));
        } catch (GlobalExcecao e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
