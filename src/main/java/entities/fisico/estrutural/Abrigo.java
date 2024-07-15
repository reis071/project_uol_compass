package entities.fisico.estrutural;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import entities.abstrato.Doacao;
import entities.abstrato.Endereco;

@Entity
public class Abrigo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private int ocupacao;

    @OneToMany(mappedBy = "abrigo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Doacao> doacoes = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    public Abrigo(){}

    public Abrigo(String nome, String responsavel, String telefone, String email,int capacidade) {
        this.nome = nome;
        this.responsavel = responsavel;
        this.telefone = telefone;
        this.email = email;
        this.capacidade = capacidade;
    }
    // Getters e setters
    public Long getId() { return id; }


    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }


    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Set<Doacao> getDoacoes() {
        return doacoes;
    }

    public void setDoacoes(Set<Doacao> doacoes) {
        this.doacoes = doacoes;
    }

    // Método para contar itens recebidos pelo abrigo
    public long countAlimentos() {
        return doacoes.stream()
                .mapToLong(doacao -> doacao.getAlimentos().size())
                .sum();
    }

    public long countRoupas() {
        return doacoes.stream()
                .mapToLong(doacao -> doacao.getRoupas().size())
                .sum();
    }

    public long countHigienes() {
        return doacoes.stream()
                .mapToLong(doacao -> doacao.getHigienes().size())
                .sum();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abrigo abrigo = (Abrigo) o;
        return Objects.equals(id, abrigo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
