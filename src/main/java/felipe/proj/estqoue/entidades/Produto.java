package felipe.proj.estqoue.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private int quantidade;
    @Column
    private double precoCompra;
    @Column
    private String categoria;
    @Column
    private LocalDate dataCompra;
    @Column
    private LocalDate  dataValidade;

    @ManyToMany
    @JoinTable(
            name = "produto_fornecedor",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    private Set<Fornecedor> fornecedor = new HashSet<>();;

    public Produto() {}

    public Produto(String nome, int quantidade, double precoCompra, String categoria, LocalDate dataCompra, LocalDate dataValidade,   Set<Fornecedor> fornecedor) {

        this.nome = nome;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        this.categoria = categoria;
        this.dataCompra = dataCompra;
        this.dataValidade = dataValidade;
        this.fornecedor = fornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public   Set<Fornecedor> getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(  Set<Fornecedor> fornecedor) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return quantidade == produto.quantidade && Double.compare(precoCompra, produto.precoCompra) == 0 && Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome) && Objects.equals(categoria, produto.categoria) && Objects.equals(fornecedor, produto.fornecedor) && Objects.equals(dataCompra, produto.dataCompra) && Objects.equals(dataValidade, produto.dataValidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, quantidade, precoCompra, categoria, fornecedor, dataCompra, dataValidade);
    }

    @Override
    public String toString() {
        return "Produto{" +
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

    public boolean isAtivo() {
        return false;
    }
}