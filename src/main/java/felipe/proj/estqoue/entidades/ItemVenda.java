package felipe.proj.estqoue.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ItemVenda")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantidade;

    @Column
    private BigDecimal precoUnitario;

    @Column
    private BigDecimal valorTotalItem;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public ItemVenda(){}

    public ItemVenda(Long id, Integer quantidade, BigDecimal precoUnitario, BigDecimal valorTotalItem, Venda venda, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.valorTotalItem = valorTotalItem;
        this.venda = venda;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(BigDecimal valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", valorTotalItem=" + valorTotalItem +
                ", venda=" + venda +
                ", produto=" + produto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Objects.equals(id, itemVenda.id) && Objects.equals(quantidade, itemVenda.quantidade) && Objects.equals(precoUnitario, itemVenda.precoUnitario) && Objects.equals(valorTotalItem, itemVenda.valorTotalItem) && Objects.equals(venda, itemVenda.venda) && Objects.equals(produto, itemVenda.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidade, precoUnitario, valorTotalItem, venda, produto);
    }
}
