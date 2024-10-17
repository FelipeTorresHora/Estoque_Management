package felipe.proj.estqoue.entidades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String fornecedorNome;
    @Column
    private String numero;
    @Column
    private String avaliacao;
    @ManyToMany(mappedBy = "fornecedor")
    private Set<Produto> produto = new HashSet<>();;

    public Fornecedor() {}

    public Fornecedor(int id, String fornecedorNome, String numero, String avaliacao, Set<Produto> produto) {
        this.id = id;
        this.fornecedorNome = fornecedorNome;
        this.numero = numero;
        this.avaliacao = avaliacao;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFornecedorNome() {
        return fornecedorNome;
    }

    public void setFornecedorNome(String fornecedorNome) {
        this.fornecedorNome = fornecedorNome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Set<Produto> getProduto() {
        return produto;
    }

    public void setProduto(Set<Produto> produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor that = (Fornecedor) o;
        return id == that.id && Objects.equals(fornecedorNome, that.fornecedorNome) && Objects.equals(numero, that.numero) && Objects.equals(avaliacao, that.avaliacao) && Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fornecedorNome, numero, avaliacao, produto);
    }

    @Override
    public String toString() {
        return "fornecedor{" +
                "id=" + id +
                ", fornecedorNome='" + fornecedorNome + '\'' +
                ", numero='" + numero + '\'' +
                ", avaliacao='" + avaliacao + '\'' +
                ", produto=" + produto +
                '}';
    }
}
