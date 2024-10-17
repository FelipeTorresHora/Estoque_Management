package felipe.proj.estqoue.request;

import felipe.proj.estqoue.entidades.Fornecedor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class AtualizarProdutoRequest {
    private Long id;
    private String nome;
    private int quantidade;
    private double precoCompra;
    private String categoria;
    private Fornecedor fornecedor;
    private LocalDate dataCompra;
    private LocalDate dataValidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public String
    toString() {
        return "AddProdutoResquest{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", precoCompra=" + precoCompra +
                ", categoria='" + categoria + '\'' +
                ", fornecedor=" + fornecedor +
                ", dataCompra=" + dataCompra +
                ", dataValidade=" + dataValidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtualizarProdutoRequest that = (AtualizarProdutoRequest) o;
        return quantidade == that.quantidade && Double.compare(precoCompra, that.precoCompra) == 0 && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(categoria, that.categoria) && Objects.equals(fornecedor, that.fornecedor) && Objects.equals(dataCompra, that.dataCompra) && Objects.equals(dataValidade, that.dataValidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, quantidade, precoCompra, categoria, fornecedor, dataCompra, dataValidade);
    }
}
