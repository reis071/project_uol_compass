package entities.abstrato;

import entities.fisico.produtos.Alimento;
import entities.fisico.produtos.Higiene;
import entities.fisico.produtos.Roupa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Doacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable( name = "tb_produto_doacao_alimento",
            joinColumns = @JoinColumn(name = "id_doacao"),
            inverseJoinColumns = @JoinColumn(name = "id_alimento"))
    private Set<Alimento> alimentos = new HashSet<>();

    @ManyToMany
    @JoinTable( name = "tb_produto_doacao_higiene",
            joinColumns = @JoinColumn(name = "id_doacao"), inverseJoinColumns = @JoinColumn(name = "id_higiene"))
    private Set<Higiene> higienes = new HashSet<>();

    @ManyToMany
    @JoinTable( name = "tb_produto_doacao_roupa", joinColumns = @JoinColumn(name = "id_doacao"), inverseJoinColumns = @JoinColumn(name = "id_roupa"))
    private Set<Roupa> roupas = new HashSet<>();

    @OneToMany(mappedBy = "id.doacao")
    private Set<TransferenciaDoacaoCentro> transferencia = new HashSet<>();


    public Set<Alimento> getAlimentos() {
        return alimentos;
    }

    public Set<Higiene> getHigienes() {
        return higienes;
    }

    public Set<Roupa> getRoupas() {
        return roupas;
    }

    @Column(nullable = false)
    private LocalDateTime dataDoacao;

    public Doacao() {}

    public Doacao(LocalDateTime dataDoacao){
        this.dataDoacao = dataDoacao;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataDoacao() {
        return dataDoacao;
    }

    public void setDataDoacao(LocalDateTime dataDoacao) {
        this.dataDoacao = dataDoacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doacao doacao = (Doacao) o;
        return Objects.equals(id, doacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
