package felipe.proj.estqoue.service.venda;

import felipe.proj.estqoue.entidades.ItemVenda;
import felipe.proj.estqoue.entidades.Produto;
import felipe.proj.estqoue.entidades.Venda;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.repositorio.ProdutoRepositorio;
import felipe.proj.estqoue.repositorio.VendaRepositorio;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class VendaService implements IVendaService{
    private final VendaRepositorio vendaRepositorio;
    private final ProdutoRepositorio produtoRepositorio;

    public VendaService(VendaRepositorio vendaRepositorio, ProdutoRepositorio produtoRepositorio) {
        this.vendaRepositorio = vendaRepositorio;
        this.produtoRepositorio = produtoRepositorio;
    }

    @Override
    public Venda addVenda(Venda venda) {
        validarVenda(venda);
        processarItensVenda(venda);
        venda.setDataPedido(LocalDateTime.now().toLocalDate());
        calcularValorTotal(venda);
        return vendaRepositorio.save(venda);
    }

    private void processarItensVenda(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            Produto produto = produtoRepositorio.findById(item.getProduto().getId())
                    .orElseThrow(() -> new GlobalExcecao("Produto não encontrado com id: " + item.getProduto().getId()));

            atualizarEstoque(produto, item.getQuantidade());
            item.setProduto(produto);
            item.setVenda(venda);
        }
    }

    private void atualizarEstoque(Produto produto, int quantidade) {
        if (produto.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoRepositorio.save(produto);
    }

    private void calcularValorTotal(Venda venda) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemVenda item : venda.getItens()) {

            BigDecimal precoTotal = BigDecimal.valueOf(item.getProduto().getPrecoVenda() * item.getQuantidade());
            item.setValorTotalItem(precoTotal);
            valorTotal = valorTotal.add(precoTotal);
        }
        venda.setValorTotal(valorTotal);
    }

    private void validarVenda(Venda venda) {
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um produto.");
        }
    }

    @Override
    public void deleteVenda(Long id) {
        vendaRepositorio.findById(id)
                .ifPresentOrElse(vendaRepositorio::delete, () -> {
                    throw new GlobalExcecao("Não foi possível encontrar essa venda!");
                });
    }

    @Override
    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = vendaRepositorio.findById(id);
        if (vendaExistente.isPresent()) {
            Venda venda = vendaExistente.get();
            venda.setValorTotal(vendaAtualizada.getValorTotal());
            venda.setDataPedido(vendaAtualizada.getDataPedido());
            venda.setCustoEnvio(vendaAtualizada.getCustoEnvio());
            venda.getItens().clear();
            venda.getItens().addAll(vendaAtualizada.getItens());
            calcularValorTotal(venda);
            return vendaRepositorio.save(venda);
        } else {
            throw new GlobalExcecao("Venda não encontrada");
        }
    }

    @Override
    public List<Venda> getAllVenda() {
        return vendaRepositorio.findAll();
    }

    @Override
    public Venda getVendaById(Long id) {
        return vendaRepositorio.findById(id)
                .orElseThrow(() -> new GlobalExcecao("Não foi possível encontrar essa venda!"));
    }
}