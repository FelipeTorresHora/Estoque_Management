package felipe.proj.estqoue.entidades;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate dataPedido;

    @Column
    private BigDecimal valorTotal;

    @Column
    private BigDecimal custoEnvio;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    public Venda() {}

    public Venda(Long id, LocalDate dataPedido, BigDecimal valorTotal, BigDecimal custoEnvio, List<ItemVenda> itens) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.custoEnvio = custoEnvio;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getCustoEnvio() {
        return custoEnvio;
    }

    public void setCustoEnvio(BigDecimal custoEnvio) {
        this.custoEnvio = custoEnvio;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataPedido=" + dataPedido +
                ", valorTotal=" + valorTotal +
                ", custoEnvio=" + custoEnvio +
                ", itens=" + itens +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id) && Objects.equals(dataPedido, venda.dataPedido) && Objects.equals(valorTotal, venda.valorTotal) && Objects.equals(custoEnvio, venda.custoEnvio) && Objects.equals(itens, venda.itens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataPedido, valorTotal, custoEnvio, itens);
    }
}
